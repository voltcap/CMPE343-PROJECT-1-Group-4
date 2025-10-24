// task b secondary school

static void secschoolMenu() {
    Scanner sc = new Scanner(System.in);
    while (true) {
        clearConsole();
        System.out.println("\nOption B Secondary School");
        System.out.println("[A] Prime Numbers");
        System.out.println("[B] Evaluation of Expression");
        System.out.println("[C] Return to Main Menu");
        System.out.print("Select a task: ");
        String opt = sc.nextLine().trim().toUpperCase();

        switch (opt) {
            case "A" -> primeNums();
            case "B" -> expressionEv();
            case "C" -> { clearConsole(); callMenu(); return; }
            default -> System.err.println("Wrong, try again");
        }
    }
}

// prime nums
static void primeNums() {
    Scanner sc = new Scanner(System.in);
    int n;
    while (true) {
        System.out.print("Enter an integer greater than 12: ");
        try {
            n = Integer.parseInt(sc.nextLine().trim());
            if (n >= 12) break;
        } catch (Exception ignored) {}
        System.err.println("That's wrong, please try again");
    }

    long t1 = System.currentTimeMillis();
    List<Integer> e = sieveEratosthenes(n);
    long t2 = System.currentTimeMillis();
    List<Integer> s = sieveSundaram(n);
    long t3 = System.currentTimeMillis();
    List<Integer> a = sieveAtkin(n);
    long t4 = System.currentTimeMillis();

    System.out.println("\n Eratosthenes Algorithm (" + (t2 - t1) + " ms)");
    printPrimeSample(e);
    System.out.println("\n Sundaram Algorithm (" + (t3 - t2) + " ms)");
    printPrimeSample(s);
    System.out.println("\n Atkin Algorithm (" + (t4 - t3) + " ms)");
    printPrimeSample(a);

    System.out.print("\nPress enter to go back");
    sc.nextLine();
}

static void printPrimeSample(List<Integer> primes) {
    if (primes.size() < 5) System.out.println(primes);
    else {
        System.out.println("First 3: " + primes.subList(0, 3));
        System.out.println("Last 2: " + primes.subList(primes.size() - 2, primes.size()));
    }
}

static List<Integer> sieveEratosthenes(int n) {
    boolean[] prime = new boolean[n + 1];
    Arrays.fill(prime, true);
    prime[0] = prime[1] = false;
    for (int i = 2; i * i <= n; i++)
        if (prime[i])
            for (int j = i * i; j <= n; j += i)
                prime[j] = false;
    List<Integer> list = new ArrayList<>();
    for (int i = 2; i <= n; i++) if (prime[i]) list.add(i);
    return list;
}

static List<Integer> sieveSundaram(int n) {
    int m = (n - 1) / 2;
    boolean[] marked = new boolean[m + 1];
    for (int i = 1; i <= m; i++)
        for (int j = i; (i + j + 2 * i * j) <= m; j++)
            marked[i + j + 2 * i * j] = true;
    List<Integer> list = new ArrayList<>();
    if (n > 2) list.add(2);
    for (int i = 1; i <= m; i++)
        if (!marked[i]) list.add(2 * i + 1);
    return list;
}

static List<Integer> sieveAtkin(int n) {
    boolean[] sieve = new boolean[n + 1];
    int sqrt = (int)Math.sqrt(n);
    for (int x = 1; x <= sqrt; x++) {
        for (int y = 1; y <= sqrt; y++) {
            int num = 4*x*x + y*y;
            if (num <= n && (num % 12 == 1 || num % 12 == 5))
                sieve[num] ^= true;
            num = 3*x*x + y*y;
            if (num <= n && num % 12 == 7)
                sieve[num] ^= true;
            num = 3*x*x - y*y;
            if (x > y && num <= n && num % 12 == 11)
                sieve[num] ^= true;
        }
    }
    for (int i = 5; i <= sqrt; i++)
        if (sieve[i])
            for (int j = i*i; j <= n; j += i*i)
                sieve[j] = false;
    List<Integer> list = new ArrayList<>();
    if (n > 2) list.add(2);
    if (n > 3) list.add(3);
    for (int i = 5; i <= n; i++) if (sieve[i]) list.add(i);
    return list;
}

// expression eval
static void expressionEv() {
    Scanner sc = new Scanner(System.in);
    while (true) {
        System.out.print("Enter an expression or press q to quit: ");
        String expr = sc.nextLine().trim();
        if (expr.equalsIgnoreCase("q")) return;

        if (!isValidExpression(expr)) {
            System.err.println("Wrong expression, make sure you're using the correct operations.\n");
            continue;
        }
        expr = expr.replace('x', '*').replace(':', '/');
        System.out.println("Step-by-step evaluation:");
        recursiveEval(expr);
        System.out.print("\nPress enter to go back");
        sc.nextLine();
        return;
    }
}

static boolean isValidExpression(String s) {
    if (!s.matches("[0-9+\\-x:\\(\\)\\s]+")) return false;
    int bal = 0;
    for (char c : s.toCharArray()) {
        if (c == '(') bal++;
        else if (c == ')') bal--;
        if (bal < 0) return false;
    }
    return bal == 0;
}

static void recursiveEval(String expr) {
    expr = expr.replaceAll("\\s+", "")
               .replace('x', '*')
               .replace(':', '/');
    try {
        while (expr.contains("(")) {
            int close = expr.indexOf(')');
            int open = expr.lastIndexOf('(', close);
            String inner = expr.substring(open + 1, close);
            double val = evaluateFlat(inner);
            String before = expr;
            expr = expr.substring(0, open) + val + expr.substring(close + 1);
            System.out.println(before + "  →  " + expr);
        }
        double result = evaluateFlat(expr);
        System.out.println("= " + result);
    } catch (Exception e) {
        System.err.println("Something's wrong: " + e.getMessage());
    }
}


static double evaluateFlat(String expr) {
    List<String> tokens = new ArrayList<>();
    StringBuilder num = new StringBuilder();
    for (int i = 0; i < expr.length(); i++) {
        char c = expr.charAt(i);
        if (Character.isDigit(c) || c == '.' || (c == '-' && (i == 0 || "+-*/".indexOf(expr.charAt(i-1)) != -1))) {
            num.append(c);
        } else if ("+-*/".indexOf(c) != -1) {
            tokens.add(num.toString());
            tokens.add(String.valueOf(c));
            num.setLength(0);
        }
    }
    tokens.add(num.toString());

    for (int i = 0; i < tokens.size(); i++) {
        if (tokens.get(i).equals("*") || tokens.get(i).equals("/")) {
            double left = Double.parseDouble(tokens.get(i-1));
            double right = Double.parseDouble(tokens.get(i+1));
            double val = tokens.get(i).equals("*") ? left * right : left / right;
            tokens.set(i-1, Double.toString(val));
            tokens.remove(i); tokens.remove(i); i--;
        }
    }

    double result = Double.parseDouble(tokens.get(0));
    for (int i = 1; i < tokens.size(); i += 2) {
        String op = tokens.get(i);
        double val = Double.parseDouble(tokens.get(i+1));
        if (op.equals("+")) result += val;
        else result -= val;
    }
    return result;
}



