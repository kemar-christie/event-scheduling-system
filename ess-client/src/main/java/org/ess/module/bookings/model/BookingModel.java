package org.ess.module.bookings.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
public class BookingModel {
    private long id;
    private long eventId;
    private long assetId;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String updatedAt;
    private String createdAt;
}
