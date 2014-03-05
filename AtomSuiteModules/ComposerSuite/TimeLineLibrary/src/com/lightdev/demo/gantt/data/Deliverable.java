package com.lightdev.demo.gantt.data;

import java.io.Serializable;
import java.util.Date;

import com.lightdev.demo.gantt.Activity;

/**
 * A deliverable in the context of project plans.
 * 
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License,
 *      for details see file license.txt in the distribution package of this software
 *
 * @version 1, 30.03.2006
 */
public class Deliverable implements Serializable, Activity {
  
  /**
   * create a new instance of a Deliverable object
   */
  public Deliverable() {
    super();
  }
  
  /**
   * get a string representation of this object
   * @return  this object as a string
   */
  public String toString() {
    return name;
  }
  
  /* ----------------------- getters / setters start ------------------------- */
  
  public Date getPlanStartDate() {
    return planStartDate;
  }
  
  public void setPlanStartDate(Date planStartDate) {
    this.planStartDate = planStartDate;
  }
  
  public double getMd() {
    return md;
  }
  
  public void setMd(double md) {
    this.md = md;
  }
  
  public double getCost() {
    return cost;
  }
  
  public void setCost(double cost) {
    this.cost = cost;
  }
  
  public Date getEndDate() {
    return endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public double getPctDone() {
    return pctDone;
  }
  
  public void setPctDone(double pctDone) {
    this.pctDone = pctDone;
  }
  
  public double getPlanMd() {
    return planMd;
  }
  
  public void setPlanMd(double planMd) {
    this.planMd = planMd;
  }
  
  public double getPlanCost() {
    return planCost;
  }
  
  public void setPlanCost(double planCost) {
    this.planCost = planCost;
  }
  
  public Date getPlanEndDate() {
    return planEndDate;
  }
  
  public void setPlanEndDate(Date planEndDate) {
    this.planEndDate = planEndDate;
  }
  
  public Date getStartDate() {
    return startDate;
  }
  
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  
  public Integer getStatus() {
    return status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public Integer getPlanId() {
    return planId;
  }
  
  public void setPlanId(Integer planId) {
    this.planId = planId;
  }
  
  public double getRestMd() {
    return restMd;
  }
  
  public void setRestMd(double restMd) {
    this.restMd = restMd;
  }
  
  public double getRestCost() {
    return restCost;
  }
  
  public void setRestCost(double restCost) {
    this.restCost = restCost;
  }
  
  public Date getRestEnd() {
    return restEnd;
  }
  
  public void setRestEnd(Date restEnd) {
    this.restEnd = restEnd;
  }
  
  public Integer getPlanVersionId() {
    return planVersionId;
  }
  
  public void setPlanVersionId(Integer planVersionId) {
    this.planVersionId = planVersionId;
  }
    
  /* ----------------------- getters / setters end ------------------------- */
  
  /* ----------------------- Activity implementation end ------------------ */
  
  public String getName() {
    return name;
  }
  
  public Date getStart() {
    return planStartDate;
  }
  
  public Date getEnd() {
    return planEndDate;
  }  
  
  public int getType() {
    return type;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setStart(Date start) {
    setPlanStartDate(start);
  }

  public void setEnd(Date end) {
    setPlanEndDate(end);
  }

  public void setType(int type) {
    this.type = type;
  }

  /* ----------------------- Activity implementation end ------------------ */
  
  /* ----------------------- class fields start ------------------ */
  
  private String name = "";
  private Date planEndDate = new Date();
  private Date planStartDate = new Date();
  private double planMd = 0;
  private double planCost = 0;
  private Date startDate = new Date();
  private Date endDate = new Date();
  private double cost = 0;
  private double pctDone = 0;
  private double md = 0 ;
  private Integer status = 0;
  private Integer planId = 0;
  private double restMd = 0;
  private double restCost = 0;
  private Date restEnd= new Date();
  private Integer planVersionId = 0;
  private int type = 0;
  
  /* ----------------------- class fields end ------------------ */
  
}
