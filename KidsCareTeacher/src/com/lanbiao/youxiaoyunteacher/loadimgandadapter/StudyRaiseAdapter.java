package com.lanbiao.youxiaoyunteacher.loadimgandadapter;

import java.util.HashMap;
import java.util.List;

import com.lanbiao.youxiaoyunteacher.entity.StudyRaise;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StudyRaiseAdapter extends ArrayAdapter<StudyRaise> {

	private static final String TAG = "ImageAndTextListAdapter";
	private ListView listView;
	public HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
	List<StudyRaise> listData;

	public StudyRaiseAdapter(Activity activity, List<StudyRaise> studyRaises,
			ListView listView) {
		super(activity, 0, studyRaises);
		this.listView = listView;
		this.listData = studyRaises;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		return parent;

	}

}
