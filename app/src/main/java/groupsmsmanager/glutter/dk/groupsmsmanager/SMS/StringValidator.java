package groupsmsmanager.glutter.dk.groupsmsmanager.SMS;

/**
 *
 * Created by izbrannick on 23-02-2015.
 * Edited by izbrannick on 10-12-2016.
 */

import java.util.ArrayList;

import groupsmsmanager.glutter.dk.groupsmsmanager.MyGroup;
import groupsmsmanager.glutter.dk.groupsmsmanager.StaticDB;

import static groupsmsmanager.glutter.dk.groupsmsmanager.StaticDB.myGroups_;
import static groupsmsmanager.glutter.dk.groupsmsmanager.StaticDB.resign;
import static groupsmsmanager.glutter.dk.groupsmsmanager.StaticDB.signup;
import static groupsmsmanager.glutter.dk.groupsmsmanager.StaticDB.words;

public class StringValidator {


    /**
     * Returns true if number is foreign. Updates currSenderNumber_ number in StaticDB
     * @param number
     * @return
     */
    public static boolean isForeignNumber(String number)
    {
        boolean b = true;
        if (number != null) {

            if (number.startsWith("+") && number.startsWith("+" + StaticDB.currentCountryCode)) {
                StaticDB.currSenderNumber_ = formatNumber(number);
                b = false;
            }
            if (number.startsWith("00") && number.startsWith("00" + StaticDB.currentCountryCode)) {
                StaticDB.currSenderNumber_ = formatNumber(number);
                b = false;
            }
            if (number.length() == 8) {
                b = false;
            }

        }
        return b;
    }

    private static String formatNumber(String number)
    {
        String formattedNumber = "";
        int length = number.length();
        if (length > 8)
        {
            formattedNumber = number.subSequence(length-8, length).toString();
        }
        return formattedNumber;
    }

    // checks if message contains requested signup / resign frazes
    /**
     * Return if true / false and updates current group name
     * [0]Signup [1]Group Name [2]Name
     * @param message
     * @return
     */
    public static boolean isSignup(String message)
    {
        words = null;
        if (!message.isEmpty()) {
            if (message.length() > 1) {
                String[] splitedMessage = message.split(" ");
                if (splitedMessage.length > 1) {
                    if (splitedMessage[0].equalsIgnoreCase(signup)) {
                        words = new ArrayList<>();
                        for (int i = 0; i < splitedMessage.length; i++) {
                            words.add(splitedMessage[i]);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // checks if message contains requested resign fraze
    public static boolean isResign(String message)
    {
        words = null;
        if (!message.isEmpty()) {
            if (message.length() > 1) {
                String[] splitedMessage = message.split(" ");
                if (splitedMessage.length > 1) {
                    if (splitedMessage[0].equalsIgnoreCase(resign)) {
                        words = new ArrayList<>();
                        for (int i = 0; i < splitedMessage.length; i++) {
                            words.add(splitedMessage[i]);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isGroupMessage(String message)
    {
        words = null;
        if (!message.isEmpty()) {
            if (message.length() > 1) {
                String[] splitedMessage = message.split(" ");
                if (splitedMessage.length > 1) {
                    words = new ArrayList<>();
                    for (int i = 0; i < splitedMessage.length; i++)
                        words.add(splitedMessage[i]);
                    if (isAGroup(words.get(0))) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isCreateGroup(String message)
    {
        if (!message.isEmpty() && message.startsWith("create group ")) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isTesting(String message)
    {
        if (message.equalsIgnoreCase("test")) {
            return true;
        }
        return false;
    }

    private static boolean isAGroup(String groupName) {
        if (myGroups_ != null) {
            if (myGroups_.size() > 0) {
                for (int i = 0; i < myGroups_.size(); i++) {

                    if (myGroups_.get(i) != null) {
                        String grName = myGroups_.get(i).getGroupName();
                        if (grName.length() > 0 && grName.equalsIgnoreCase(groupName)) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public static MyGroup getCurrentGroup(String groupMessage_) {
        if (myGroups_ != null) {
            if (groupMessage_.length() > 1) {
                if (myGroups_.size() > 0) {
                    for (int i = 0; i < myGroups_.size(); i++) {
                        String splitMessage[] = groupMessage_.split(" ", 2);
                        if (splitMessage.length > 0) {
                            if (splitMessage[0].equalsIgnoreCase(myGroups_.get(i).getGroupName())) {
                                MyGroup mGroup = myGroups_.get(i);
                                String grName = mGroup.getGroupName();
                                if (mGroup != null) {
                                    return mGroup;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}