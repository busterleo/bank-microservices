package com.bank.customer.domain.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerPage {
  private final List<Customer> content;

  private final Integer page;

  private final Integer size;

  private final Long totalElements;

  private final Integer totalPages;
}
