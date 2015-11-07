package no.hials.taskserver.impl;

/**
 * Grading Response message
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-11-01 
 */
public class GradingResponseMsg extends MessageImpl {
    // Predefined keys
    public final static String KEY_CURRENT_PERCENT = "percent";
    public final static String KEY_BEST_GRADE = "finalGrade";
    public final static String KEY_PASS = "pass";
    public final static String KEY_STUD = "studentName";
    public final static String KEY_COMMENT = "comment";
    // Predefined value for the command parameter
    public final static String CMD = "showGrade";

    public GradingResponseMsg(boolean forSending) {
        super(forSending);
        setCommand(CMD);        
    }

    /**
     * Create a GradingResponse message from message content.
     * Can be used to cast from plain MessageImpl to specific GradingResponse
     * @param srcMsg source message to be copied
     * @return 
     */
    public static GradingResponseMsg createFrom(MessageImpl srcMsg) {
        GradingResponseMsg msg = new GradingResponseMsg(false);
        msg.cloneFrom(srcMsg);
        return msg;
    }
    
    /**
     * Set current grade 
     * @param grade current grade (in this session) in percent
     */
    public void setCurrentGrade(int grade) {
        setParamValue(KEY_CURRENT_PERCENT, String.valueOf(grade));
    }

    /**
     * Return grade (percent) or -1 if it is not found in the message
     * @return 
     */
    public int getCurrentGrade() {
        return getIntParam(KEY_CURRENT_PERCENT);
    }
    
    public void setStudentName(String studName) {
        setParamValue(KEY_STUD, studName);
    }

    public String getStudentName() {
        return getParamValue(KEY_STUD);
    }
    
    public void setComment(String comment) {
        setParamValue(KEY_COMMENT, comment);
    }

    public String getComment() {
        return getParamValue(KEY_COMMENT);
    }
    
    /**
     * Set the best overall grade
     * @param bestGrade best grade over this and all the previous sessions
     */
    public void setBestGrade(int bestGrade) {
        setParamValue(KEY_BEST_GRADE, String.valueOf(bestGrade));
    }

    /**
     * Return stored best-ever grade for this student
     * @return 
     */
    public int getBestGrade() {
        return getIntParam(KEY_BEST_GRADE);
    }
    
    /**
     * Set pass/not-pass
     * @param pass 
     */
    public void setPass(boolean pass) {
        setParamValue(KEY_PASS, pass ? "true" : "false");
    }
    
    /**
     * @return true of the student has passed the exercise (based on all 
     * responses in all sessions)
     */
    public boolean isPass() {
        return getBoolParam(KEY_PASS);
    }
}
