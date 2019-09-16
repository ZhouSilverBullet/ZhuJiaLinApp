package com.sdxxtop.trackerlibrary.listener;


import androidx.fragment.app.Fragment;

public class LifecycleFragment extends Fragment implements IChangeName {

	protected OnFragmentVisibleListener listener;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (listener != null && isResumed()) {
			listener.onVisibleChanged(this, isVisibleToUser);
		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (listener != null) {
			listener.onVisibleChanged(this, !hidden);
		}
	}

	@Override
	public String changeAppendType() {
		return DEFAULT_TYPE;
	}

}
