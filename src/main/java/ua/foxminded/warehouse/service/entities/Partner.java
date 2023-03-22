package ua.foxminded.warehouse.service.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.foxminded.warehouse.service.enums.PartnerType;

/**
 * Represents a business partner, such as a supplier or a customer. Each partner
 * has a unique ID, name, and taxpayer identification number (TIN), as well as
 * additional information such as payment method, payment terms, discount,
 * address, type, and notes.
 *
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
@Table(name = "partners")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 10)
    @Column(unique = true)
    private String tin;

    @Size(max = 20)
    @Column(name = "payment_method")
    private String paymentMethod;

    @Size(max = 20)
    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "discount_percent")
    @Positive
    private BigDecimal discountPercent;

    @OneToMany(mappedBy = "partner", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Address> address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PartnerType type;

    @Size(max = 100)
    private String notes;

    @OneToMany(mappedBy = "partner", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Transaction> transactions;
}
