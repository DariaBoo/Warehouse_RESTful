package ua.foxminded.warehouse.service.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a unit of measurement used for items in the warehouse. Each unit has a unique ID and name.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "units")
public class Unit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(unique = true)
    private String name;
}
