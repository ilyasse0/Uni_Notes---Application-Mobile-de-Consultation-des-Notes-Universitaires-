package com.boukouch.mini_projet.data

object EndPoints {
    private val URL_ROOT = "http://10.0.2.2/UMI_DB"
    val link_login ="$URL_ROOT/auth/login.php"
    val link_checkemail ="$URL_ROOT/ResetPassword/CheckEmail.php"
    val link_verifycompte ="$URL_ROOT/ResetPassword/verifycompte.php"
    val link_newpassword ="$URL_ROOT/ResetPassword/resetpassword.php"
    val link_profil ="$URL_ROOT/profil/profile.php"
}