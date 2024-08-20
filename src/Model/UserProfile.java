package Model;

import javax.swing.*;

public class UserProfile {
    // Builder pattern
    private String nickname;
    private int punteggio;
    private int round; // livello

    private UserProfile(String nickname, int punteggio, int round){
        this.nickname = nickname;
        this.punteggio = punteggio;
        this.round = round;
    }

    public String getNickname() {
        return this.nickname;
    }
    public int getPunteggio(){
        return this.punteggio;
    }
    public int getRound(){
        return this.round;
    }
    public static class UserProfileBuilder{
        private String nickname;
        private int punteggio;
        private int round; // livello

        public UserProfileBuilder setNickname(String nickname){
            this.nickname = nickname;
            return this;
        }
        public UserProfileBuilder setPunteggio(int punteggio){
            this.punteggio = punteggio;
            return this;
        }
        public UserProfileBuilder setRound(int round){
            this.round = round;
            return this;
        }
        public UserProfile build(){
            return new UserProfile(nickname,punteggio,round);
        }
    }
}
