
package Resourse;

import java.sql.Time;

public class Variable {
    private static String ActualUser;
    private static Time UltimateTime;

    public static String getActualUser() {
        return ActualUser;
    }

    public static void setActualUser(String ActualUser) {
        Variable.ActualUser = ActualUser;
    }

    public static Time getUltimateTime() {
        return UltimateTime;
    }

    public static void setUltimateTime(Time UltimateTime) {
        Variable.UltimateTime = UltimateTime;
    }
}
