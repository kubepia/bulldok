package io.nogada.sambulldok.auth;

/**
 * User
 */
public class User {
    public User(){

    }
    public User(final String id){
        this.id=id;
        final String[] set = {
            "Charlie",
            "Brown",
            "Sunny",
            "Alex",
            "Grace"
            };
        final int n = (int) (Math.random() * 100) % 5;
        this.name= set[n];
        this.description = " If you use this site regularly and would like to help keep the site on the Internet, please consider donating a small sum to help pay for the hosting and bandwidth bill.";
    }
    String id;
    String name;
    String description;
}