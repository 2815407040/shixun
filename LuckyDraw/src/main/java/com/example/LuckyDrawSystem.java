package com.example;
import java.util.*;

public class LuckyDrawSystem {
    private static Map<String, User> userMap = new HashMap<>();
    private static User currentUser = null;
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    static class User {
        private String username;
        private String password;
        private int memberId;

        public User(String username, String password, int memberId) {
            this.username = username;
            this.password = password;
            this.memberId = memberId;
        }

        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public int getMemberId() { return memberId; }
    }

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            int choice = getMenuChoice();
            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    drawLottery();
                    break;
                default:
                    System.out.println("您的输入有误!");
            }
            isRunning = askContinue();
        }
        System.out.println("系统退出,谢谢使用!");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n****欢迎进入奖客富翁系统****");
        System.out.println("1.注册  2.登录  3.抽奖");
        System.out.println("*************************");
        System.out.print("请选择菜单:");
    }

    private static int getMenuChoice() {
        try {
            return Integer.parseInt(scanner.next().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean askContinue() {
        System.out.print("继续吗?(y/n):");
        String input = scanner.next().trim().toLowerCase();
        return "y".equals(input);
    }

    private static void register() {
        System.out.println("[奖客富翁系统>注册]");
        System.out.println("请填写个人注册信息:");

        System.out.print("用户名:");
        String username = scanner.next().trim();
        if (userMap.containsKey(username)) {
            System.out.println("该用户名已存在!");
            return;
        }

        System.out.print("密码:");
        String password = scanner.next().trim();

        int memberId = random.nextInt(10);

        User newUser = new User(username, password, memberId);
        userMap.put(username, newUser);

        System.out.println("注册成功,请记好您的会员卡号");
        System.out.println("用户名:" + username + " 密码:" + password + " 会员卡号:" + memberId);
    }

    private static void login() {
        System.out.println("[奖客富翁系统>登录]");
        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            System.out.print("请输入用户名:");
            String username = scanner.nextLine().trim();
            System.out.print("请输入密码:");
            String password = scanner.nextLine().trim();

            User user = userMap.get(username);
            if (user != null && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("欢迎您:" + username);
                return;
            }

            attempts++;
            int remaining = maxAttempts - attempts;
            if (remaining > 0) {
                System.out.println("用户名或密码错误，剩余" + remaining + "次机会");
            } else {
                System.out.println("三次登录失败，系统退出");
                System.exit(0);
            }
        }
    }

    private static void drawLottery() {
        if (currentUser == null) {
            System.out.println("请先登录系统!");
            return;
        }

        System.out.println("[奖客富翁系统>抽奖]");
        int userCard = currentUser.getMemberId();
        System.out.println("您的卡号为:" + userCard);

        Set<Integer> luckyNumbers = new HashSet<>();
        while (luckyNumbers.size() < 5) {
            int num = random.nextInt(10); // 0-9的1位数字
            luckyNumbers.add(num);
        }

        System.out.print("本日的幸运数字为:");
        for (int num : luckyNumbers) {
            System.out.print(num + " ");
        }
        System.out.println();

        if (luckyNumbers.contains(userCard)) {
            System.out.println("恭喜!您是本日的幸运会员!");
        } else {
            System.out.println("抱歉!您不是本日的幸运会员!");
        }
    }
}