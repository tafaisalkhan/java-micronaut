package com.micronet.controller;

import com.micronet.dto.LoginRequest;
import com.micronet.dto.LoginResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.Authenticator;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import reactor.core.publisher.Mono;

@Controller("/auth")
public class AuthController {

    private final Authenticator<HttpRequest<?>> authenticator;
    private final JwtTokenGenerator jwtTokenGenerator;

    public AuthController(Authenticator<HttpRequest<?>> authenticator, JwtTokenGenerator jwtTokenGenerator) {
        this.authenticator = authenticator;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Get("/login")
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Produces(MediaType.TEXT_HTML)
    public String loginPage() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Login - Demo</title>
                <style>
                    * { margin: 0; padding: 0; box-sizing: border-box; }
                    body { 
                        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
                        background: #f0f2f5; display: flex; justify-content: center; 
                        align-items: center; min-height: 100vh;
                    }
                    .login-box {
                        background: white; padding: 40px; border-radius: 12px;
                        box-shadow: 0 2px 12px rgba(0,0,0,0.1); width: 360px;
                    }
                    h2 { text-align: center; margin-bottom: 24px; color: #1a1a1a; }
                    .field { margin-bottom: 16px; }
                    label { display: block; margin-bottom: 6px; font-weight: 500; color: #333; }
                    input { 
                        width: 100%; padding: 10px 12px; border: 1px solid #d1d5db;
                        border-radius: 6px; font-size: 14px; outline: none;
                        transition: border-color 0.2s;
                    }
                    input:focus { border-color: #2563eb; }
                    button {
                        width: 100%; padding: 12px; background: #2563eb; color: white;
                        border: none; border-radius: 6px; font-size: 15px; font-weight: 500;
                        cursor: pointer; margin-top: 8px; transition: background 0.2s;
                    }
                    button:hover { background: #1d4ed8; }
                    #message { margin-top: 16px; text-align: center; font-size: 13px; }
                    .error { color: #dc2626; }
                    .success { color: #16a34a; }
                </style>
            </head>
            <body>
                <div class="login-box">
                    <h2>🔐 Login</h2>
                    <div class="field">
                        <label for="username">Username</label>
                        <input type="text" id="username" placeholder="Enter username" autocomplete="username">
                    </div>
                    <div class="field">
                        <label for="password">Password</label>
                        <input type="password" id="password" placeholder="Enter password" autocomplete="current-password">
                    </div>
                    <button onclick="doLogin()">Login</button>
                    <div id="message"></div>
                </div>
                <script>
                    async function doLogin() {
                        const msg = document.getElementById('message');
                        msg.className = '';
                        msg.textContent = '';
                        const username = document.getElementById('username').value;
                        const password = document.getElementById('password').value;
                        if (!username || !password) {
                            msg.className = 'error';
                            msg.textContent = 'Please enter username and password';
                            return;
                        }
                        try {
                            const res = await fetch('/auth/login', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/json' },
                                body: JSON.stringify({ username, password })
                            });
                            const data = await res.json();
                            if (res.ok) {
                                msg.className = 'success';
                                msg.innerHTML = '✓ Login successful!<br>Token: <code style="word-break:break-all;font-size:11px">' + data.token + '</code>';
                                localStorage.setItem('jwt', data.token);
                            } else {
                                msg.className = 'error';
                                msg.textContent = data.message || 'Login failed';
                            }
                        } catch (e) {
                            msg.className = 'error';
                            msg.textContent = 'Network error';
                        }
                    }
                    document.getElementById('password').addEventListener('keydown', function(e) {
                        if (e.key === 'Enter') doLogin();
                    });
                </script>
            </body>
            </html>
            """;
    }

    @Post("/login")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public Mono<MutableHttpResponse<LoginResponse>> login(@Body LoginRequest request, HttpRequest<?> httpRequest) {
        UsernamePasswordCredentials credentials =
                new UsernamePasswordCredentials(request.username(), request.password());

        return Mono.from(authenticator.authenticate(httpRequest, credentials))
                .map(response -> {
                    if (!response.isAuthenticated()) {
                        return HttpResponse.<LoginResponse>unauthorized()
                                .body(new LoginResponse(null, request.username(), response.getMessage().orElse("Invalid credentials")));
                    }
                    Authentication auth = response.getAuthentication().orElseThrow();
                    String token = jwtTokenGenerator.generateToken(auth, null).orElseThrow();
                    return HttpResponse.ok(new LoginResponse(token, request.username(), "Login successful"));
                })
                .onErrorResume(e -> Mono.just(
                        HttpResponse.<LoginResponse>unauthorized()
                                .body(new LoginResponse(null, request.username(), "Authentication failed"))));
    }
}
