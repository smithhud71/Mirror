/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mirrorrooms;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author Hud Smith
 * 06/17/18 - Mirror Test Project for Pioneer
 * This is a console application that takes in a configuration file
 * from that file - it creates a grid of rooms
 * then it assigns mirrors to rooms with orientation and reflective properties
 * then it figures out the starting location and direction of start
 * finally it calls the getnext functionality until it cant find a next one 
 */

public class MirrorRooms {

    /**
     * @param args the command line arguments
     */

    static RoomList rlWork;

    static String sRooms;  //rooms parameter stored from input file
    static ArrayList<String> sMirrors = new ArrayList<>();  //mirror orientations parameter stored from input file
    static String sEntry;  //start parameter stored from inputfile

    public static class RoomList
    {
        public Room[][] roomList;
        int iX;
        int iY;
        Room rExitRoom;
        String sExitDirection;
        String sError;
        int iRoomEntryCount = 0;
        int iUniqueRoomEntryCount = 0;
        int iEntryX = -1;
        int iEntryY = -1;
        String sEntryDirection = "N/A";
        
        public RoomList(int ax, int ay)
        {
            //Function or initializing Rooms based on array listing
            roomList = new Room[ax][ay];
            iX = ax;
            iY = ay;
            for(int iXList = 0; iXList < iX; iXList++)
            {
                for(int iYList = 0; iYList < iY; iYList++)
                {
                    roomList[iXList][iYList] = new Room(iXList, iYList);
                }               
            }
        }
        
        boolean GetNextRoom(int axCurrent, int ayCurrent, String aEntryLocation)
        {
            //Function for determining the next room based on origination and direction and then 
            //  using mirror orientation and reflection
            boolean abReturn = false;
            Room lCurrRoom = roomList[axCurrent][ayCurrent];
            int ixNext = axCurrent;
            int iyNext = ayCurrent;
            String sNextEntryLocation = "N/A";
            switch (aEntryLocation)
            {
                case "TOP":
                    if (lCurrRoom.bTopEntry)
                    {
                        sError = aEntryLocation + " for room " + axCurrent + "," + ayCurrent + " has already been entered so infinite loop - no exit";
                    }
                    else
                    {
                        lCurrRoom.bTopEntry = true;
                        if (lCurrRoom.cMirrorLean == 'L' && lCurrRoom.cMirrorReflection != 'L' )
                        {
                            ixNext = ixNext + 1;
                            sNextEntryLocation = "LEFT";
                        }
                        else if (lCurrRoom.cMirrorLean == 'R' && lCurrRoom.cMirrorReflection != 'R' )
                        {
                            ixNext = ixNext - 1;
                            sNextEntryLocation = "RIGHT";
                        }
                        else
                        {
                            iyNext = iyNext - 1;
                            sNextEntryLocation = "TOP";
                        }
                    }
                    break;
                case "RIGHT":
                    if (lCurrRoom.bRightEntry)
                    {
                        sError = aEntryLocation + " for room " + axCurrent + "," + ayCurrent + " has already been entered so infinite loop - no exit";
                    }
                    else
                    {
                        lCurrRoom.bRightEntry = true;
                        if (lCurrRoom.cMirrorLean == 'L' && lCurrRoom.cMirrorReflection != 'L' )
                        {
                            iyNext = iyNext + 1;
                            sNextEntryLocation = "BOTTOM";
                        }
                        else if (lCurrRoom.cMirrorLean == 'R' && lCurrRoom.cMirrorReflection != 'L' )
                        {
                            iyNext = iyNext - 1;
                            sNextEntryLocation = "TOP";
                        }
                        else
                        {
                            ixNext = ixNext - 1;
                            sNextEntryLocation = "RIGHT";
                        }
                    }
                    break;
                case "BOTTOM":
                    if (lCurrRoom.bBottomEntry)
                    {
                        sError = aEntryLocation + " for room " + axCurrent + "," + ayCurrent + " has already been entered so infinite loop - no exit";
                    }
                    else
                    {
                        lCurrRoom.bBottomEntry = true;
                        if (lCurrRoom.cMirrorLean == 'L' && lCurrRoom.cMirrorReflection != 'R' )
                        {
                            ixNext = ixNext - 1;
                            sNextEntryLocation = "RIGHT";
                        }
                        else if (lCurrRoom.cMirrorLean == 'R' && lCurrRoom.cMirrorReflection != 'L' )
                        {
                            ixNext = ixNext + 1;
                            sNextEntryLocation = "LEFT";
                        }
                        else
                        {
                            iyNext = iyNext + 1;
                            sNextEntryLocation = "BOTTOM";
                        }
                    }
                    break;
                case "LEFT":
                    if (lCurrRoom.bLeftEntry)
                    {
                        sError = aEntryLocation + " for room " + axCurrent + "," + ayCurrent + " has already been entered so infinite loop - no exit";
                    }
                    else
                    {
                        lCurrRoom.bLeftEntry = true;
                        if (lCurrRoom.cMirrorLean == 'L' && lCurrRoom.cMirrorReflection != 'R' )
                        {
                            iyNext = iyNext - 1;
                            sNextEntryLocation = "TOP";
                        }
                        else if (lCurrRoom.cMirrorLean == 'R' && lCurrRoom.cMirrorReflection != 'R' )
                        {
                            iyNext = iyNext + 1;
                            sNextEntryLocation = "BOTTOM";
                        }
                        else
                        {
                            ixNext = ixNext + 1;
                            sNextEntryLocation = "LEFT";
                        }
                    }
                    break;
                default:
                    //should never happen
                    sError = aEntryLocation + " is not a proper entry location";
            }
            if (sError != null)
            {
                sNextEntryLocation = "N/A";
                abReturn = false;
                System.out.println(sError + " with the last room index of " + axCurrent + "," + ayCurrent);
            }
            else
            {
                if (ixNext < 0 )
                {
                    System.out.println("Came out of room index of " + axCurrent + "," + ayCurrent + " Horizontially");                    
                }
                else if (ixNext >= roomList.length)
                {
                    System.out.println("Came out of room index of " + axCurrent + "," + ayCurrent + " Horizontially");                    
                }
                else if (iyNext < 0 )
                {
                    System.out.println("Came out of room index of " + axCurrent + "," + ayCurrent + " Vertically");                    
                }
                else if (iyNext >= roomList[0].length)
                {
                    System.out.println("Came out of room index of " + axCurrent + "," + ayCurrent + " Vertically");                    
                }
                else
                {
                    abReturn = true;
                    iRoomEntryCount+=1;
                    if (!roomList[axCurrent][ayCurrent].bHasEntered)
                    {
                        iUniqueRoomEntryCount+=1;
                        roomList[axCurrent][ayCurrent].bHasEntered = true;
                    }
                    System.out.println("Entry " + iRoomEntryCount + " = Came out of room index of " + axCurrent + "," + ayCurrent + " with Entry Point " + aEntryLocation + " and leaving to room index of " + ixNext + ", " + iyNext + " and Entry Point " + sNextEntryLocation );
                    this.GetNextRoom(ixNext, iyNext, sNextEntryLocation);
                }
            }
            return abReturn;
        //Room rExitRoom;
        //String sExitDirection;
        //String sError;
        //int iRoomEntryCount = 0;
        //int iUniqueRoomEntryCount = 0;
        }
    }
    public static class Room
    {
        //Ojbect used to contain each instance of the room
        int iX = -1; //Contains the X Coordinate of the Room
        int iY = -1; //Contains the Y Coordinate of the Room
        boolean bhasMirror = false; //States whether the Room has a Mirror
        char cMirrorLean = 'U'; //States the direction the Mirror leans in the Room
        char cMirrorReflection = 'U'; //States the Reflection Designation of the Mirror in the Room
        boolean bHasEntered = false; //States whether the room has been entered before - used for uniqueness
        boolean bTopEntry = false; //States whether the room has been entered from this side
        boolean bRightEntry = false; //States whether the room has been entered from this side
        boolean bBottomEntry = false; //States whether the room has been entered from this side
        boolean bLeftEntry = false; //States whether the room has been entered from this side
        
        Room(int ax, int ay)
        {
            //Definition of Room must have an X and Y coordinate
            //Since the application is creating rooms in sequential order based on input
            //Should be no reason to check for duplates or issues with Values of X or Y
            iX = ax;
            iY = ay;
        }        
    }
    
    public static void main(String[] args) {
        // Default Entry point of Application 
        // Check to see if filename was included in args and if so Process
        //  --New code 06/16/18 HAS 
        String sFileName;
        boolean bProcessFileSuccess;
        boolean bRoomSetupSuccess;
        
        if (args.length <= 0)
        {
            System.out.println("Please add the Configurate Filename to the Command Line Arguments");
        } 
        else
        {
            File fInput = new File(args[0]);
            if (fInput.exists())
            {
                sFileName = args[0];
                System.out.println("Configuration Filename " + sFileName + " being processed...");
                bProcessFileSuccess = ProcessFile(sFileName);
                if (bProcessFileSuccess)
                {
                    bRoomSetupSuccess = RoomSetup();
                    if (bRoomSetupSuccess)
                        rlWork.GetNextRoom(rlWork.iEntryX, rlWork.iEntryY, rlWork.sEntryDirection);
                        //rlWork.GetNextRoom(4, 3, "TOP");
                }
            }
            else
            {
                System.out.println("Configuration Filename " + args[0] + " does not Exist.  Please check location of file and try running command again.");
            }
        }
    }
    
    static public boolean RoomSetup()
    {
        String sError="";
        int iX=-1;
        int iY=-1;
        int iEntryX = -1;
        int iEntryY = -1;
        String sEntryDirection = "N/A";
        int iMirrorX = -1;
        int iMirrorY = -1;
        char cMirrorDirection = 'U';
        char cMirrorReflection = 'U';
        try 
        {
            String[] sXY = sRooms.split(",");
            if (sXY.length == 2)
            {
                try
                {
                    iX = Integer.parseInt(sXY[0]);
                } 
                catch (NumberFormatException e) 
                {
                    sError = "Room Size definition does not contain a good value for X";
                }
                try
                {
                    iY = Integer.parseInt(sXY[1]);
                } 
                catch (NumberFormatException e) 
                {
                    sError = "Room Size definition does not contain a good value for Y";
                }
                if (iX > 0 && iY > 0)
                {
                    rlWork = new RoomList(iX, iY);
                }
                else
                {
                    sError = "Room Size X and Y have to be greater than 0";
                }
            }

            String[] sEntryXY = sEntry.split(",");
            if (sEntryXY.length == 2)
            {
                try
                {
                    iEntryX = Integer.parseInt(sEntryXY[0]);
                } 
                catch (NumberFormatException e) 
                {
                    sError = "Room Entry definition does not contain a good value for X";
                }
                try
                {
                    if (sEntryXY[1].length()>1)
                    {
                        sEntryDirection = sEntryXY[1].substring(sEntryXY[1].length()-1, sEntryXY[1].length());
                        String sTempY = sEntryXY[1].substring(0, sEntryXY[1].length()-1);
                        iEntryY = Integer.parseInt(sTempY);
                    }
                }
                catch (NumberFormatException e) 
                {
                    sError = "Room Size definition does not contain a good value for Y";
                }
                if (iX > 0 && iY > 0)
                {
                    rlWork = new RoomList(iX, iY);
                }
                else
                {
                    sError = "Room Size X and Y have to be greater than 0";
                }
            }
            if (sError == "")
            {
                rlWork.iEntryX = iEntryX;
                rlWork.iEntryY = iEntryY;
                if (iEntryX == 0 && sEntryDirection.contains("H"))
                    rlWork.sEntryDirection = "LEFT";
                else if (sEntryDirection.contains("H"))
                    rlWork.sEntryDirection = "LEFT";
                else if (iEntryY == 0 && sEntryDirection.contains("V"))
                    rlWork.sEntryDirection = "BOTTOM";
                else if (sEntryDirection.contains("V"))
                    rlWork.sEntryDirection = "TOP";
                else
                    rlWork.sEntryDirection = "N/A";
                Iterator<String> itr = sMirrors.iterator();
                while(itr.hasNext())
                {
                    iMirrorX = -1;
                    iMirrorY = -1;
                    cMirrorDirection = 'U';
                    cMirrorReflection = 'U';
                    String sTempY = "";
                    String sTemp = itr.next();
                    String[] sMirrorXY = sTemp.split(",");
                   
                    if (sMirrorXY.length == 2)
                    {
                        try
                        {
                            iMirrorX = Integer.parseInt(sMirrorXY[0]);
                        } 
                        catch (NumberFormatException e) 
                        {
                            sError = "Mirror Room Entry definition does not contain a good value for X";
                        }
                        try
                        {
                            if (sMirrorXY[1].length()>1)
                            {
                                if (sMirrorXY[1].contains("LR"))
                                {
                                    cMirrorDirection = 'L';
                                    cMirrorReflection = 'R';
                                    sTempY = sMirrorXY[1].substring(0, sMirrorXY[1].length()-2);
                                }
                                else if (sMirrorXY[1].contains("LL"))
                                {
                                    cMirrorDirection = 'L';
                                    cMirrorReflection = 'L';
                                    sTempY = sMirrorXY[1].substring(0, sMirrorXY[1].length()-2);
                                }
                                else if (sMirrorXY[1].contains("RR"))
                                {
                                    cMirrorDirection = 'R';
                                    cMirrorReflection = 'R';
                                    sTempY = sMirrorXY[1].substring(0, sMirrorXY[1].length()-2);
                                }
                                else if (sMirrorXY[1].contains("RL"))
                                {
                                    cMirrorDirection = 'R';
                                    cMirrorReflection = 'L';
                                    sTempY = sMirrorXY[1].substring(0, sMirrorXY[1].length()-2);
                                }
                                else if (sMirrorXY[1].contains("L"))
                                {
                                    cMirrorDirection = 'L';
                                    cMirrorReflection = 'U';
                                    sTempY = sMirrorXY[1].substring(0, sMirrorXY[1].length()-1);
                                }
                                else if (sMirrorXY[1].contains("R"))
                                {
                                    cMirrorDirection = 'R';
                                    cMirrorReflection = 'U';
                                    sTempY = sMirrorXY[1].substring(0, sMirrorXY[1].length()-1);
                                }
                                iMirrorY = Integer.parseInt(sTempY);
                            }
                            rlWork.roomList[iMirrorX][iMirrorY].bhasMirror = true;
                            rlWork.roomList[iMirrorX][iMirrorY].cMirrorLean = cMirrorDirection;
                            rlWork.roomList[iMirrorX][iMirrorY].cMirrorReflection = cMirrorReflection;
                            System.out.println("Added Mirror locaion " + iMirrorX + "," + iMirrorY + ", with Lean -" + cMirrorDirection + " and Reflection - " + cMirrorReflection);
                        }
                        catch (NumberFormatException e) 
                        {
                            sError = "Room Size definition does not contain a good value for Y";
                        }
                    }
                }
                return true;
            }
            else
            {
                return false;
            }
        } 
        catch (Exception e) 
        {
                e.printStackTrace();
                return false;
        }
    }

    static public boolean ProcessFile(String asFile)
    {
        try 
        {
            int iLineNumber=0;
            int iMinusCount=0;
            FileReader fileReader = new FileReader(asFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String sline;
            while ((sline = bufferedReader.readLine()) != null) {
                iLineNumber+=1;
                System.out.println(iLineNumber + " - Entry =" + sline);
                if (iLineNumber==1)
                {
                   sRooms = sline;
                }
                if (sline.contains("-1"))
                {
                    iMinusCount+=1;
                }
                else if (iMinusCount == 1)
                {
                    sMirrors.add(sline);
                }
                else if (iMinusCount == 2)
                {
                    sEntry = sline;
                }
            }
            fileReader.close();
            if (iMinusCount ==3)
                return true;
            else
                return false;
        } 
        catch (IOException e) 
        {
                e.printStackTrace();
                return false;
        }
    }
}
