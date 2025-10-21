// 定义接口 ICommon
interface ICommon {
    double calculateSpeed(int A, int B, int C);
    double calculateTime(int A, int B, int C);
}

// 实现 Car 类
class Car implements ICommon {
    @Override
    public double calculateSpeed(int A, int B, int C) {
        return (double) A * B / C;
    }

    @Override
    public double calculateTime(int A, int B, int C) {
        return 1000 / calculateSpeed(A, B, C);
    }
}

// 实现 Plane 类
class Plane implements ICommon {
    @Override
    public double calculateSpeed(int A, int B, int C) {
        return A + B + C;
    }

    @Override
    public double calculateTime(int A, int B, int C) {
        return 1000 / calculateSpeed(A, B, C);
    }
}
public class SixB {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("使用方法: java SixB <工具类型> <整数A> <整数B> <整数C>");
            return;
        }

        String vehicleType = args[0];
        int A = Integer.parseInt(args[1]);
        int B = Integer.parseInt(args[2]);
        int C = Integer.parseInt(args[3]);

        try {
            // 尝试根据车辆类型创建实例
            Class<?> clazz = Class.forName(vehicleType);
            ICommon vehicle = (ICommon) clazz.getDeclaredConstructor().newInstance();

            // 计算并输出时间，保留两位小数
            double time = vehicle.calculateTime(A, B, C);
            System.out.printf("运行1000米所需的时间: %.2f 秒%n", time);
        } catch (ClassNotFoundException e) {
            System.out.println("找不到指定的交通工具类型: " + vehicleType);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("创建交通工具实例时出错");
        }
    }
}
