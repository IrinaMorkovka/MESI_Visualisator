/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

/**
 *
 * @author Vasiliy
 */
public enum MESI_Transitions {
    EXCLUSIVE_TO_EXCLUSIVE_READ,
    SHARED_TO_SHARED_READ,
    MODIFIED_TO_MODIFIED_READ,
    READ_REQUEST,
    READING_FROM_MEMORY,
    INVALID_TO_EXCLUSIVE,
    INVALID_TO_SHARED,
    EXCLUSIVE_TO_SHARED,
    SHARED_TO_SHARED,
    WRITE_TO_MEMORY,
    MODIFIED_TO_SHARED,
    MODIFIED_TO_MODIFIED_WRITE,
    EXCLUSIVE_TO_MODIFIED,
    INVALIDATE_REQUSET,
    INVALID_TO_MODIFIED,
    SHARED_TO_MODIFIED,
    EXCLUSIVE_TO_INVALID,
    SHARED_TO_INVALID,
    MODIFIED_TO_INVALID
}
