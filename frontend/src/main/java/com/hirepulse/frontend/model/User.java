package com.hirepulse.frontend.model;

public class User {

    public enum Role {
        CANDIDATE("Candidate", "🧳"),
        EMPLOYER("Employer", "🏢"),
        ADMIN("Admin", "⚡");

        private final String displayName;
        private final String icon;

        Role(String displayName, String icon) {
            this.displayName = displayName;
            this.icon = icon;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getIcon() {
            return icon;
        }
    }

    private String username;
    private String password;
    private String fullName;
    private String email;
    private Role role;
    private String avatarUrl;

    public User() {
    }

    public User(String username, String password, String fullName, String email, Role role, String avatarUrl) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
