package com.samsung.videorental;

import java.util.Date;

public class Rental {
    private Video video;
    private int status; // 0 for Rented, 1 for Returned
    private Date rentDate;
    private Date returnDate;

    public Rental(Video video) {
        this.video = video;
        status = 0;
        rentDate = new Date();
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public int getStatus() {
        return status;
    }

    public void returnVideo() {
        if (status == 1) {
            this.status = 1;
            returnDate = new Date();
        }
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getDaysRentedLimit() {
        int limit = 0;
        int daysRented;
        if (getStatus() == 1) { // returned Video
            long diff = returnDate.getTime() - rentDate.getTime();
            daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
        } else { // not yet returned
            long diff = new Date().getTime() - rentDate.getTime();
            daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
        }
        if (daysRented <= 2) return limit;

        switch (video.getVideoType()) {
            case Video.VHS:
                limit = 5;
                break;
            case Video.CD:
                limit = 3;
                break;
            case Video.DVD:
                limit = 2;
                break;
        }
        return limit;
    }
    
    public int getDaysRented() {
    	int daysRented = 0;
    	long diff = 0;
    	
    	if (getStatus() == 1) { // returned Video
            diff = getReturnDate().getTime() - getRentDate().getTime();
        } else { // not yet returned
            diff = new Date().getTime() - getRentDate().getTime();
        }
    	daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
    	
    	return daysRented;
    }
    
    public double getCharge() {
    	double charge = 0;

        switch (getVideo().getPriceCode()) {
            case Video.REGULAR:
            	charge += 2;
                if (getDaysRented() > 2)
                	charge += (getDaysRented() - 2) * 1.5;
                break;
            case Video.NEW_RELEASE:
            	charge = getDaysRented() * 3;
                break;
        }
        
        return charge;
    }
    
    public int getPoint() {
    	int point = 0;
    	
    	point++;

        if ((getVideo().getPriceCode() == Video.NEW_RELEASE))
        	point++;

        if (getDaysRented() > getDaysRentedLimit())
        	point -= Math.min(point, getVideo().getLateReturnPointPenalty());
        
        return point;
    }
    
    public String getRentalReport() {
    	String report = "\t" + getVideo()
        .getTitle() + "\tDays rented: " + getDaysRented() + "\tCharge: " + getCharge()
        + "\tPoint: " + getPoint() + "\n";
    	
    	return report;
    }
}
