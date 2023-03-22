package ua.foxminded.warehouse.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A generic response class that can be used to represent the response from an API call.
 *
 * @param <T> the type of data returned in the response
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FinalResponse<T> {

    private String message;
    private T data;
    private List<T> dataList;
}
