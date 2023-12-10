package com.boukouch.mini_projet.data

object EndPoints {
    private val URL_ROOT = "https://kotlinboukouchtest.000webhostapp.com"

    //Login
    val link_login ="$URL_ROOT/auth/login.php"

    //Resetpasseword
    val link_checkemail ="$URL_ROOT/ResetPassword/CheckEmail.php"
    val link_verifycompte ="$URL_ROOT/ResetPassword/verifycompte.php"
    val link_newpassword ="$URL_ROOT/ResetPassword/resetpassword.php"

    //Profile
    val link_profil ="$URL_ROOT/profil/profile.php"
    val link_changepassword ="$URL_ROOT/profil/edit_password.php"

    //SHow Notes
    val link_selectnotes ="$URL_ROOT/universite/Select_Note.php"

    //Annonces
    val link_select_annonces ="$URL_ROOT/annonce/select_annonce.php"



}