package com.sdxxtop.trackerlibrary.listener;

import androidx.fragment.app.Fragment;

public interface OnFragmentVisibleListener {

	/**
	 * Fragment的onHidden或setUserVisibleHint被调用时触发
	 * @param visible
	 */
	void onVisibleChanged(Fragment f, boolean visible);

}
