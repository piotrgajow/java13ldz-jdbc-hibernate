package sda;

public class Place {

    public Integer id;
    public String city;
    public String address;
    public String name;
    public Boolean cloakRoom;
    public Boolean parking;

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", cloakRoom=" + cloakRoom +
                ", parking=" + parking +
                '}';
    }

}
