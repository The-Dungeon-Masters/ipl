package com.dungeon.master.ipl.dto;

public class UserChangePassword {
    long userId;
    String currentPassword;
    String newPassword;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserChangePassword that = (UserChangePassword) o;

        if (userId != that.userId) return false;
        if (currentPassword != null ? !currentPassword.equals(that.currentPassword) : that.currentPassword != null)
            return false;
        return newPassword != null ? newPassword.equals(that.newPassword) : that.newPassword == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (currentPassword != null ? currentPassword.hashCode() : 0);
        result = 31 * result + (newPassword != null ? newPassword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserChangePassword{" +
                "userId=" + userId +
                ", currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
