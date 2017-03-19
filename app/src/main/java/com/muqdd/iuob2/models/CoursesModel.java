package com.muqdd.iuob2.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.muqdd.iuob2.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ali Yusuf on 3/12/2017.
 * iUOB-2
 */

public class CoursesModel extends AbstractItem<CoursesModel, CoursesModel.ViewHolder> {

    public String title;
    public String prog;
    public String abv;
    public String inl;
    public String courseNumber;
    public String credits;
    public String year;
    public String semester;
    public String pre;

    public CoursesModel(String href, String title, String pre) {
        this.title = title;
        this.pre = pre;
        String pattern = "\\?.*?=(.*)?&.*?=(.*)?&.*?=(.*)?&.*?=(.*)?&.*?=(.*)?&.*?=(.*)?&.*?=(.*)?";
        Matcher m = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE).matcher(href);
        if(m.find()) {
            this.prog = m.group(1);
            this.abv = m.group(2);
            this.inl = m.group(3);
            this.courseNumber = m.group(4);
            this.credits = m.group(5);
            this.year = m.group(6);
            this.semester = m.group(7);
        }
    }

    public CoursesModel(String title, String prog, String abv, String inl, String courseNumber,
                        String credits, String year, String semester, String pre) {
        this.title = title;
        this.prog = prog;
        this.abv = abv;
        this.inl = inl;
        this.courseNumber = courseNumber;
        this.credits = credits;
        this.year = year;
        this.semester = semester;
        this.pre = pre;
    }

    //The unique ID for this type of item
    @Override
    public int getType() {
        return R.id.course_title;
    }

    //The layout to be used for this type of item
    @Override
    public int getLayoutRes() {
        return R.layout.row_course;
    }

    //The logic to bind your data to the view
    @Override
    public void bindView(CoursesModel.ViewHolder viewHolder, List<Object> payloads) {
        //call super so the selection is already handled for you
        super.bindView(viewHolder, payloads);
        viewHolder.title.setText(title);
        if (pre == null || "".equals(pre)) {
            viewHolder.pre.setVisibility(View.GONE);
        } else {
            viewHolder.pre.setText(pre);
            viewHolder.pre.setVisibility(View.VISIBLE);
        }
    }

    //reset the view here (this is an optional method, but recommended)
    @Override
    public void unbindView(CoursesModel.ViewHolder holder) {
        super.unbindView(holder);
        holder.title.setText("");
        holder.pre.setText("");
        holder.pre.setVisibility(View.GONE);
    }

    //The viewHolder used for this item. This viewHolder is always reused by the RecyclerView so scrolling is blazing fast
    static class ViewHolder extends RecyclerView.ViewHolder {
        protected @BindView(R.id.course_title) TextView title;
        protected @BindView(R.id.course_pre) TextView pre;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}