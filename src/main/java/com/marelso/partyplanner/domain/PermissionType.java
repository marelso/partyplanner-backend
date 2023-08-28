package com.marelso.partyplanner.domain;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.List.of;

public enum PermissionType {
    SUPER("SUPER", of("NONE", "USER", "SUPER")),
    USER("USER", of("NONE", "USER")),
    NONE("NONE", emptyList());

    private final String permissionId;
    private final List<String> permissions;

    PermissionType(String permissionId, List<String> permissions) {
        this.permissionId = permissionId;
        this.permissions = permissions;
    }

    public boolean hasPermission(PermissionType permisssion) {
        return this.permissions
                .stream()
                .anyMatch(p -> p.equals(permisssion.permissionId));
    }
}