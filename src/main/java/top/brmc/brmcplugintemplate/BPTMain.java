package top.brmc.brmcplugintemplate;

import top.brmc.devlib.plugin.SimplePlugin;

/**
 * BlockLand Realms 插件开发模板的主类.
 *
 * <p>继承 {@link SimplePlugin}（top.brmc.devlib.plugin.SimplePlugin）.
 * SimplePlugin 表示一个使用增强库功能的基础 Java 插件,
 *
 * <p>可重写的生命周期回调（均由 SimplePlugin 自动调度,详见其 Javadoc）：
 * <ul>
 *   <li>{@code onPluginLoad()} —— 插件启动前调用（对应 JavaPlugin#onLoad()）</li>
 *   <li>{@code onReloadablesStart()} —— 插件启动、执行 /reload 或调用
 *       SimplePlugin#reload() 时调用,用于注册命令、事件、任务和文件</li>
 *   <li>{@code onPluginStart()} —— 主加载方法,插件准备好加载时调用（只执行一次）</li>
 *   <li>{@code onPluginPreReload()} —— 在重载设置之前调用</li>
 *   <li>{@code onPluginReload()} —— 在重载设置之后调用</li>
 *   <li>{@code onPluginStop()} —— 插件即将关闭时调用</li>
 * </ul>
 */
public final class BPTMain extends SimplePlugin {

    /**
     * 主加载方法,在插件准备好加载时调用（整个生命周期只执行一次）.
     *
     * <p>在 onEnable 流程中于 {@link #onReloadablesStart()} 之后执行,
     * 适合放置只需运行一次的初始化逻辑（例如数据库连接、定时任务创建）.
     */
    @Override
    protected void onPluginStart() {
    }

    /**
     * 在此注册你的命令、事件、任务和文件.
     *
     * <p>插件启动、执行 /reload 或调用 SimplePlugin#reload() 时都会调用此方法,
     * 适合放置需要随重载重新注册的内容.
     */
    @Override
    protected void onReloadablesStart() {

        // 可以在这里检查必要插件是否已安装,缺失则抛出错误中止加载.
        // Valid 是"检查条件并抛出安全异常、且异常会记入文件"的工具类,
        // checkBoolean 在表达式为 false 时抛出带自定义消息的错误：
        // Valid.checkBoolean(HookManager.isVaultLoaded(), "需要安装 Vault 才能处理数据包、离线玩家数据、前缀和分组.");

        // 如需使用变量系统,加载 variables/ 文件夹中的所有变量：
        // Variable.loadVariables();

        //
        // 在此添加需要随插件自动加载的其它部分.
        // 标注了 @AutoRegister 的类无需手动注册：
        // 将该注解放在 SimpleListener、PacketListener、BungeeListener、
        // DiscordListener、SimpleCommand、SimpleCommandGroup、SimpleExpansion、
        // YamlConfig（启动时自动加载配置并随重载刷新）或任意
        // "implements Listener" 的类上,Foundation 会在插件启动时
        // 自动注册它,并在重载时正确刷新；
        // Tool（如 Rocket）与 SimpleEnchantment 则无论是否标注都会自我注册.
        //
    }

    /**
     * 在重载设置之前调用.
     *
     * <p>如果使用了数据库,适合在此关闭连接,避免重载时残留占用.
     */
    @Override
    protected void onPluginPreReload() {

        // 如果使用了数据库,在此关闭：
        // YourDatabase.getInstance().close();
    }

    /* ------------------------------------------------------------------------------- */
    /* 静态方法 */
    /* ------------------------------------------------------------------------------- */

    /**
     * 返回本插件的实例.
     *
     * <p>SimplePlugin 在 onLoad 时已维护好静态实例,此处直接返回
     * {@link SimplePlugin#getInstance()}.Javadoc 建议在自己的实现中
     * 覆盖此方法,直接返回该实现的实例（例如将返回类型改为 BPTMain）.
     *
     * @return 当前插件实例
     */
    public static SimplePlugin getInstance() {
        return SimplePlugin.getInstance();
    }
}