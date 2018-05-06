package com.sms.society.controller.dto.society;

import java.util.List;

public class RegisterDTO {
    public static class Wing {
        private String name;

        private List<String> rooms;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getRooms() {
            return rooms;
        }

        public void setRooms(List<String> rooms) {
            this.rooms = rooms;
        }
    }

    public static class Secretary {
        private String wing;

        private String room;

        private String mobile;

        public String getWing() {
            return wing;
        }

        public void setWing(String wing) {
            this.wing = wing;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    private String name;

    private List<Wing> wings;

    private Secretary secretary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Wing> getWings() {
        return wings;
    }

    public void setWings(List<Wing> wings) {
        this.wings = wings;
    }

    public Secretary getSecretary() {
        return secretary;
    }

    public void setSecretary(Secretary secretary) {
        this.secretary = secretary;
    }
}
