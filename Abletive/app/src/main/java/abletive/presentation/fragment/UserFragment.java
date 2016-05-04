package abletive.presentation.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import abletive.businesslogic.blutil.UserData;
import abletive.presentation.activity.LogActivity;
import abletive.presentation.activity.MainActivity;
import abletive.presentation.activity.PersonInfoActivity;
import abletive.presentation.activity.PersonalPageActivity;
import abletive.vo.UserVO;
import alandelip.abletivedemo.R;
import jp.wasabeef.blurry.Blurry;

/**
 * 用户界面碎片
 */
public class UserFragment extends Fragment {

    private View currentView;
    private UserVO userVO;
    private UserData userData;
    private TextView userName;
    private Toolbar toolbar;
    private Button personalInfoButton;
    private Button collectionsButton;
    private ImageView mAvatarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        currentView = getView();
        userData = UserData.getInstance();
        userVO = userData.getUserVO();

        //如果没有渲染过toolbar就初始化
        if (toolbar == null) {
            initToolBar();
        }
        initButton();

        //如果登录就显示用户信息，否则显示请登录信息
        if (userData.isLogin()) {
            initUserData();
        } else {
            initLoginData();
        }


    }

    private void initButton() {
        personalInfoButton = (Button) currentView.findViewById(R.id.personal_info);
        collectionsButton = (Button) currentView.findViewById(R.id.collections);
        personalInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData.isLogin()) {
                    PersonInfoActivity.newInstance(getContext());
                }
            }
        });
        collectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转至收藏界面
            }
        });

        mAvatarView = (ImageView) currentView.findViewById(R.id.user_avatar);
        mAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userData.isLogin()) {
                    LogActivity.newInstance(getContext());
                }
            }
        });

        TextView mPersonalPageView = (TextView) currentView.findViewById(R.id.personal_page_text);
        mPersonalPageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至本人的个人界面
                PersonalPageActivity.newInstance(getContext(), userVO.getId());
            }
        });

        TextView mCardView = (TextView) currentView.findViewById(R.id.my_matrix_text);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示个人名片

            }
        });
    }

    /**
     * 初始化标头
     */
    private void initToolBar() {
        toolbar = (Toolbar) currentView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
    }

    /**
     * 初始化用户登录信息显示
     */
    private void initLoginData() {
        userName = (TextView) currentView.findViewById(R.id.user_nickname);
        //设置显示用户名为“未登录”
        userName.setText(R.string.no_login);
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userData.isLogin()) {
                    LogActivity.newInstance(getContext());
                }
            }
        });

        //设置个人资料和我的收藏按钮不可见
        View buttonGroup = currentView.findViewById(R.id.button_group);
        buttonGroup.setVisibility(View.INVISIBLE);

        //设置默认背景
        ImageView mHeaderBackground = (ImageView) currentView.findViewById(R.id.header_background);
        mHeaderBackground.setImageBitmap(null);
        mHeaderBackground.setAlpha(1f);
        mHeaderBackground.setBackgroundColor(getResources().getColor(R.color.dkred));

        //设置默认头像
        ImageView mAvatarView = (ImageView) currentView.findViewById(R.id.user_avatar);
        mAvatarView.setImageResource(R.drawable.launch_logo);

        //设置列表
        View userInfoLayout = currentView.findViewById(R.id.user_info_group);
        userInfoLayout.setVisibility(View.GONE);

        TextView loginInfo = (TextView) currentView.findViewById(R.id.log_info);
        //设置登录提醒可见
        loginInfo.setVisibility(View.VISIBLE);
        loginInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至登录界面
                LogActivity.newInstance(getContext());
            }
        });
    }

    /**
     * 初始化用户信息显示
     */
    private void initUserData() {
        //设置用户名
        userName = (TextView) currentView.findViewById(R.id.user_nickname);
        userName.setText(userVO.getNickname());

        //设置个人资料和我的收藏按钮
        View buttonGroup = currentView.findViewById(R.id.button_group);
        buttonGroup.setVisibility(View.VISIBLE);

        TextView loginInfo = (TextView) currentView.findViewById(R.id.log_info);
        //设置登录提醒不可见
        loginInfo.setVisibility(View.GONE);

        //设置头像和背景
        final ImageView mHeaderBackground = (ImageView) currentView.findViewById(R.id.header_background);
        final ImageView mAvatarView = (ImageView) currentView.findViewById(R.id.user_avatar);
        MainActivity.IMAGE_CACHE.get(userVO.getAvatarUrl(), mAvatarView);
        Bitmap loadedImage = MainActivity.IMAGE_CACHE.get(userVO.getAvatarUrl()).getData();
        mHeaderBackground.setImageBitmap(loadedImage);
        mHeaderBackground.setAlpha(Float.valueOf("0.8"));
        Blurry.with(getContext())
                .radius(20)
                .sampling(8)
                .async()
                .capture(mHeaderBackground)
                .into(mHeaderBackground);
        //设置个人资料和收藏按钮配色
        Palette palette = Palette.from(loadedImage).generate();
        Palette.Swatch muteLight = palette.getLightMutedSwatch(),
                vibrantLight = palette.getLightVibrantSwatch();
        if (muteLight != null) {
            personalInfoButton.setBackgroundColor(muteLight.getRgb());
            personalInfoButton.setTextColor(muteLight.getTitleTextColor());
            collectionsButton.setBackgroundColor(muteLight.getRgb());
            collectionsButton.setTextColor(muteLight.getTitleTextColor());
        } else if (vibrantLight != null) {
            personalInfoButton.setBackgroundColor(vibrantLight.getRgb());
            personalInfoButton.setTextColor(vibrantLight.getTitleTextColor());
            collectionsButton.setBackgroundColor(vibrantLight.getRgb());
            collectionsButton.setTextColor(vibrantLight.getTitleTextColor());
        }

        //设置列表
        View userInfoLayout = currentView.findViewById(R.id.user_info_group);
        userInfoLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sign_up) {
            //TODO 签到
            Toast.makeText(getContext(), getString(R.string.sign), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_settings) {
            //TODO 设置
            Toast.makeText(getContext(), getString(R.string.action_settings), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
