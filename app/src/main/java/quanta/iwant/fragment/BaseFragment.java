package quanta.iwant.fragment;

import java.util.ArrayList;

import quanta.iwant.activity.MainActivity;
import quanta.iwant.entity.ContentEntity;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public abstract class BaseFragment extends Fragment {
	public abstract void init(MainActivity context);
	public abstract void refresh(ArrayList<ContentEntity> contentEntities);
	public abstract void refreshByclassification();
}
