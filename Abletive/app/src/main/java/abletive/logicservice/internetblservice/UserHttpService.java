package abletive.logicservice.internetblservice;

import java.util.ArrayList;

import abletive.po.HttpDailyCheckinPO;
import abletive.po.HttpPersonalPagePO;
import abletive.po.HttpSignupPO;
import abletive.po.HttpUserPO;
import abletive.po.UserPO;
import abletive.vo.SignupVO;

/**
 * 用户相关网络逻辑接口
 *
 * @author Alan
 */
public interface UserHttpService {
    /**
     * 登录
     *
     * @param userID   用户ID
     * @param password 密码
     * @return 用户数据
     */
    HttpUserPO signin(String userID, String password);

    /**
     * 注册
     *
     * @param signupVO 注册信息
     * @return 用户数据
     */
    HttpSignupPO signup(SignupVO signupVO);

    /**
     * 获取指定用户信息
     *
     * @param userID 用户ID
     * @return 用户信息
     */
    UserPO getUserInfo(String userID);

    /**
     * 获取指定用户的个人主页信息
     *
     * @param userID        查看的某位用户的用户ID
     * @param currentUserID 当前用户ID
     * @return 个人主页信息
     */
    HttpPersonalPagePO getPersonalPage(String userID, String currentUserID);

    /**
     * 获取指定用户的关注者列表
     *
     * @param userID        用户ID
     * @param currentUserID 查看者的用户ID
     * @param page          第几页
     * @return 关注者列表
     */
    ArrayList<UserPO> getFollowList(String userID, String currentUserID, int page);

    /**
     * 每日签到
     *
     * @param userID 用户ID
     * @return 签到信息
     */
    HttpDailyCheckinPO dailyCheckin(String userID);

    /**
     * 关注用户动作
     *
     * @param userID        关注的用户ID
     * @param currentUserID 当前的用户ID
     * @param act           关注动作(follow/unfollow)
     * @return 是否成功（1/0）
     */
    int follow(String userID, String currentUserID, String act);


}
