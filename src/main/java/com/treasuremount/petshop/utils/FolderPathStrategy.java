package com.treasuremount.petshop.utils;

@FunctionalInterface
public interface FolderPathStrategy {
    String getFolderPath(Long entityId);
}
