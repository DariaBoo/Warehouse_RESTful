package ua.foxminded.warehouse.service.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents warehouse partner's address
 * @author Bohush Darya
 * @version 1.0
 *
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@DynamicUpdate
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(targetEntity = Partner.class)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;
    
    @Size(max = 30)
    @Column(name = "contact_person")
    private String contactPerson;
    
    @NotBlank
    @Size(max = 50)
    @Column(name = "address")
    private String shippingAddress;
    
    @NotBlank
    @Size(max = 20)
    private String city;
    
    @NotBlank
    @Size(max = 20)
    private String state;
    
    @NotBlank
    @Size(max = 20)
    private String country;
    
    @NotBlank
    @Size(max = 20)
    @Column(name = "postal_code")
    private String postalCode;
    
    @NotBlank
    @Size(max = 15)
    private String phone;
    
    @NotBlank
    @Size(max = 20)
//    @Email(regexp = "^(.+)@(\\S+) $")
    private String email;
}
