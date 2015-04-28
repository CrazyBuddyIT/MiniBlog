package com.tony.miniblog.test;

/**
 * Used to convert a String to Object.
 * Created by Tony on 2015/3/15 0015.
 */
public class TestString2Obj {
    public static void main(String[] args) {
        User u = new User(11, "战三");
        String uS = u.toString();
        System.out.println(uS);

    }


    static class User {
        private int id;
        private String name;


        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

		@override
		public void toString(){
			StringBuilder sb = new StringBuilder();
			sb.append("User[ name="+this.name+",id="+this.id+"]");
			return sb.toString();
		}
    }
}
