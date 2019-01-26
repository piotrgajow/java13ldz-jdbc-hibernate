package sda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String city;
    public String address;
    public String name;

    @Column(name = "cloak_room")
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
