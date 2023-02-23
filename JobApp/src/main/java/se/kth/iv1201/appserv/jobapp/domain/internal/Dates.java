package se.kth.iv1201.appserv.jobapp.domain.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dates {
    String startDate;
    String endDate;
}
