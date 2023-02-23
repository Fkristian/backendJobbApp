package se.kth.iv1201.appserv.jobapp.domain.external.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.kth.iv1201.appserv.jobapp.domain.internal.Competences;
import se.kth.iv1201.appserv.jobapp.domain.internal.Dates;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {
    List<Dates> availabilityArray;
    List<Competences> competenceArray;

}

