package com.curesio.ehealth.constants;

import java.util.ArrayList;
import java.util.Arrays;

public interface Authorities {
    ArrayList<String> USER_AUTHORITIES = new ArrayList<>(Arrays.asList("user.read", "user:update"));

    ArrayList<String> HOSPITAL_ADMIN_AUTHORITIES = new ArrayList<>(
            Arrays.asList("user:read", "user:update", "staff:create", "staff:update", "staff:remove", "hospital:update"));

    ArrayList<String> HOSPITAL_STAFF_AUTHORITIES = new ArrayList<>(Arrays.asList("user:read", "user:update"));

    ArrayList<String> PHYSICIAN_ADMIN_AUTHORITIES = new ArrayList<>(
            Arrays.asList("user:read", "user:update", "staff:create", "staff:update", "staff:remove", "physician:update"));

    ArrayList<String> PHYSICIAN_STAFF_AUTHORITIES = new ArrayList<>(Arrays.asList("user:read", "user:update"));

    ArrayList<String> PHARMACY_ADMIN_AUTHORITIES = new ArrayList<>(
            Arrays.asList("user:read", "user:update", "staff:create", "staff:update", "staff:remove", "pharmacy:update"));

    ArrayList<String> PHARMACY_STAFF_AUTHORITIES = new ArrayList<>(Arrays.asList("user:read", "user:update"));

    ArrayList<String> LABORATORY_ADMIN_AUTHORITIES = new ArrayList<>(
            Arrays.asList("user:read", "user:update", "staff:create", "staff:update", "staff:remove", "laboratory:update"));

    ArrayList<String> LABORATORY_STAFF_AUTHORITIES = new ArrayList<>(Arrays.asList("user:read", "user:update"));

    ArrayList<String> ADMIN_AUTHORITIES = new ArrayList<>();

    ArrayList<String> ACCOUNT_VERIFICATION_STAFF_AUTHORITIES = new ArrayList<>();
}