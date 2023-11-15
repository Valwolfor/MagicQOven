package com.magicqoven.config;

import com.google.auth.Credentials;

import java.io.IOException;

public interface CredentialsProvider {
    Credentials getCredentials() throws IOException;
}

