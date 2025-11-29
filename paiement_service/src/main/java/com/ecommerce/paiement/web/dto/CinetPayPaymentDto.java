package com.ecommerce.paiement.web.dto;

public class CinetPayPaymentDto {

    private String apikey = "5337111116358eef42b6448.37599996";
    private String site_id = "301005";
    private String return_url = "http://127.0.0.1";
    private String notify_url = "http://127.0.0.1";
    private String mode = "TEST";

    private String id_paiement;
    private String transaction_id;
    //doit être un multiple de 5
    private int amount;
    //taille = 3 La devise monétaire (XOF, XAF, CDF, GNF, USD)
    private String currency = "XAF";
    private String description = "Paiement d'achat(s) sur Asso";
    //sert à définir les univers présent sur le guichet (ALL, MOBILE_MONEY, CREDIT_CARD, WALLET)
    private String channels = "ALL";

    private String customer_id;
    private String customer_name;
    private String customer_surname;
    private String customer_phone_number;
    private String customer_email;
    private String customer_address;
    private String customer_city;
    //taille = 2 pays lié à votre compte CinetPay code ISO du pays (code à deux chiffres) ex : CI, TG, SN
    private String customer_country = "CM";
    //taille = 2, Etat du pays dans lequel se trouve le client
    private String customer_state = "CM";
    //taille = 5 code postal du client
    private String customer_zip_code = "BP123";

    public CinetPayPaymentDto() {

    }

    public CinetPayPaymentDto(String transaction_id, int amount, String customer_id, String customer_name, String customer_surname, String customer_phone_number, String customer_email, String customer_address, String customer_city, String customer_zip_code) {
        this.transaction_id = transaction_id;
        this.amount = amount;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_surname = customer_surname;
        this.customer_phone_number= customer_phone_number;
        this.customer_email = customer_email;
        this.customer_address = customer_address;
        this.customer_city = customer_city;
        this.customer_zip_code = customer_zip_code;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getSiteId() {
        return site_id;
    }

    public void setSiteId(String siteId) {
        this.site_id = siteId;
    }

    public String getIdPaiement() {
        return id_paiement;
    }

    public void setIdPaiement(String id_paiement) {
        this.id_paiement = id_paiement;
    }

    public String getTransactionId(){
        return transaction_id;
    }

    public void setTransactionId(String transactionId) {
        this.transaction_id = transactionId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount % 5 == 0) {
            this.amount = amount;
        } else {
            throw new IllegalArgumentException("Le montant doit être un multiple de 5");
        }
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (currency != null && currency.length() == 3) {
            this.currency = currency;
        } else {
            throw new IllegalArgumentException("La taille de la chaîne doit être exactement 3 caractères.");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReturnUrl() {
        return return_url;
    }

    public void setReturnUrl(String return_url) {
        this.return_url = return_url;
    }

    public String getNotifyUrl() {
        return notify_url;
    }

    public void setNotifyUrl(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomerName() {
        return customer_name;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomerSurname() {
        return customer_surname;
    }

    public void setCustomerSurname(String customer_surname) {
        this.customer_surname = customer_surname;
    }

    public String getCustomerPhoneNumber() {
        return customer_phone_number;
    }

    public void setCustomerPhoneNumber(String customer_phone_number) {
        this.customer_phone_number = customer_phone_number;
    }

    public String getCustomerEmail() {
        return customer_email;
    }

    public void setCustomerEmail(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomerAddress() {
        return customer_address;
    }

    public void setCustomerAddress(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomerCity() {
        return customer_city;
    }

    public void setCustomerCity(String customer_city) {
        this.customer_city = customer_city;
    }

    public String getCustomerCountry(){
        return customer_country;
    }

    public void setCustomerCountry(String customer_country) {
        if(customer_country != null && customer_country.length() == 2){
            this.customer_country = customer_country;
        } else {
            throw new IllegalArgumentException("La taille de la chaîne doit être exactement 2 caractères (code ISO).");
        }
    }

    public String getCustomerState() {
        return customer_state;
    }

    public void setCustomerState(String customer_state) {
        if(customer_state != null && customer_state.length() == 2){
            this.customer_state = customer_state;
        } else {
            throw new IllegalArgumentException("La taille de la chaîne doit être exactement 2 caractères (code ISO).");
        }
    }

    public String getCustomerZipCode() {
        return customer_zip_code;
    }

    public void setCustomerZipCode(String customer_zip_code) {
        if(customer_zip_code != null && customer_zip_code.length() == 5){
            this.customer_zip_code = customer_zip_code;
        } else {
            throw new IllegalArgumentException("La taille de la chaîne doit être exactement 5 caractères.");
        }
    }

    @Override
    public String toString() {
        return "CinetPayPayment{" +
                "apikey='" + apikey + '\'' +
                ", site_id='" + site_id + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", description='" + description + '\'' +
                ", return_url='" + return_url + '\'' +
                ", notify_url='" + notify_url + '\'' +
                '}';
    }
}
