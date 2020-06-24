package com.f1soft.spring.logging.demo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author santosh
 */
@Getter
@Setter
public class CustomerDetail extends ModelBase {

    private String username;
    private String password;
    private String cardNo;

}
