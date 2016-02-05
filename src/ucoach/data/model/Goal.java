package ucoach.data.model;

import ucoach.data.dao.UcoachDataDao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The Goal class represents a Goal set by a User regarding a Health Measure he keeps track of.
 * 
 * @author anadaniel
 *
 */
@Entity
@Table(name="goal") 
public class Goal implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator="mysql_goal")
  @TableGenerator(name="mysql_goal", table="mysql_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="goal")
  @Column(name="id")
  private int id;
  @Column(name="frequency")
  private String frequency;
  @Column(name="objective")
  private String objective;
  @Column(name="value")
  private Float value;
  @Temporal(TemporalType.DATE)
  @Column(name="due_date")
  private Date dueDate;
  @Column(name="achieved")
  private String achieved;

  @ManyToOne
  @JoinColumn(name="hm_type_id",referencedColumnName="id")
  private HMType hmType;

  @ManyToOne
  @JoinColumn(name="user_id", referencedColumnName="id")
  private User user;
  
  // Getters
  public int getId(){
    return id;
  }
  public String getFrequency(){
    return frequency;
  }
  public String getObjective(){
    return objective;
  }
  public Float getValue(){
    return value;
  }
  public Date getDueDate(){
    return dueDate;
  }
  public String getAchieved(){
    return achieved;
  }
  @XmlTransient
  public User getUser(){
    return user;
  }
  public HMType getHmType(){
    return hmType;
  }
  
  // Setters
  public void setId(int id){
    this.id = id;
  }
  public void setFrequency(String frequency){
    this.frequency = frequency;
  }
  public void setObjective(String objective){
    this.objective = objective;
  }
  public void setValue(Float value){
    this.value = value;
  }
  public void setDueDate(Date dueDate){
    this.dueDate = dueDate;
  }
  public void setAchieved(String achieved){
    this.achieved = achieved;
  }
  public void setUser(User user){
    this.user = user;
  }
  public void setHmType(HMType hmType){
    this.hmType = hmType;
  }

  /**
   * Creates a Goal in the database.
   * 
   * @param Goal      The Goal to be persisted in the database.
   * @param userId    The id of the user the Goal belongs to.
   * @param hmTypeId  The id of the HM Type of the Goal.
   * @return          The created Goal.
   */
  public static Goal createGoal(Goal goal, int userId, int hmTypeId) {
    goal.setUser(User.getUserById(userId));
    goal.setHmType(HMType.getHMTypeById(hmTypeId));

    EntityManager em = UcoachDataDao.instance.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.persist(goal);
    tx.commit();
    UcoachDataDao.instance.closeConnections(em);
    return goal;
  }
}