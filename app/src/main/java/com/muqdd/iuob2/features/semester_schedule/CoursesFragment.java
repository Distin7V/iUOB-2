package com.muqdd.iuob2.features.semester_schedule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.muqdd.iuob2.R;
import com.muqdd.iuob2.app.BaseFragment;
import com.muqdd.iuob2.models.Course;
import com.muqdd.iuob2.models.CoursePrefix;
import com.muqdd.iuob2.models.RestResponse;
import com.muqdd.iuob2.network.ServiceGenerator;
import com.muqdd.iuob2.network.UOBSchedule;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ali Yusuf on 3/11/2017.
 * iUOB-2
 */

public class CoursesFragment extends BaseFragment {

    public final static String COURSE_PREFIX = "COURSE_PREFIX";

    @BindView(R.id.main_content) LinearLayout mainContent;
    @BindView(R.id.recycler_view) SuperRecyclerView recyclerView;

    private CoursePrefix coursePrefix;
    private FastItemAdapter<Course> fastAdapter;
    private View mView;

    public CoursesFragment() {
        // Required empty public constructor
    }

    public static CoursesFragment newInstance(CoursePrefix coursePrefix) {
        CoursesFragment fragment = new CoursesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, coursePrefix.getPrefix());
        bundle.putString(COURSE_PREFIX, new Gson().toJson(coursePrefix, CoursePrefix.class));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        if (mView == null) {
            // Inflate the layout for this fragment
            if (getArguments() != null) {
                coursePrefix = new Gson().fromJson(getArguments().getString(COURSE_PREFIX), CoursePrefix.class);
            }
            mView = inflater.inflate(R.layout.fragment_list, container, false);
            ButterKnife.bind(this, mView);
            initiate();
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // get coursesList list
        if (fastAdapter.getAdapterItems().size() == 0) {
            getCoursesList(coursePrefix);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // set fragment title
        toolbar.setTitle(title);
    }

    private void initiate() {
        // initialize variables
        fastAdapter = new FastItemAdapter<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation()));

        // set refreshable list
        recyclerView.getSwipeToRefresh().setEnabled(true);
        recyclerView.getSwipeToRefresh().setColorSchemeResources(
                R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryLight);

        // refresh request
        recyclerView.getSwipeToRefresh().setOnRefreshListener(() -> getCoursesList(coursePrefix));
        fastAdapter.withOnClickListener((v, adapter, item, position) -> {
            SectionsFragment fragment = SectionsFragment.newInstance(item);
            displayFragment(fragment);
            return true;
        });
    }

    public void getCoursesList(CoursePrefix request) {
        ServiceGenerator.createService(UOBSchedule.class)
                .courses(request.getYear(), request.getSemester(), request.getPrefix())
                .enqueue(new Callback<RestResponse<List<Course>>>() {
                    @Override
                    public void onResponse(@NonNull Call<RestResponse<List<Course>>> call, @NonNull final Response<RestResponse<List<Course>>> response) {
                        RestResponse<List<Course>> restResponse = response.body();
                        if (restResponse != null && restResponse.getStatusCode() == 200) {
                            fastAdapter.set(restResponse.getData());
                            if (recyclerView.getAdapter() == null) {
                                recyclerView.setAdapter(fastAdapter);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<RestResponse<List<Course>>> call, @NonNull Throwable t) {}
                });
    }

}
