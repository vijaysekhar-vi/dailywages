package com.hogslab.dailywages.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.hogslab.dailywages.R;
import com.hogslab.dailywages.activities.EmployeeProfileActivity;
import com.hogslab.dailywages.activities.MainActivity;
import com.hogslab.dailywages.pojo.EmployeeBriefPojo;

import java.util.List;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private Context context;
    List<EmployeeBriefPojo> employeeList;
    int count;
    Activity activity;
    int maxSize;

    public EmployeeAdapter(Context c, List<EmployeeBriefPojo> list, int count, Activity activity) {
        this.context = c;
        this.employeeList = list;
        this.count = count;
        this.activity = activity;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_employee, parent, false);

        return new EmployeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {

        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        holder.employeeName.setText(employeeList.get(position).getEmployeeName());
        holder.employeeRole.setText(employeeList.get(position).getEmployeeRole());

    }


    @Override
    public int getItemCount() {
        return this.count;
    }

    public void updateList(List<EmployeeBriefPojo> list) {
        this.employeeList = list;
        maxSize = list.size();
        notifyDataSetChanged();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        public TextView employeeName;
        public TextView employeeRole;
        public ImageView imageView;
        Context context;

        private static final int REQUEST_CODE_CALL_PERMISSION = 1;
        private String[] callPermArray = new String[]{Manifest.permission.CALL_PHONE};

        public EmployeeViewHolder(View itemView) {
            super(itemView);

            employeeName = itemView.findViewById(R.id.idEmployeeName);
            employeeRole = itemView.findViewById(R.id.id_employee_role);
            imageView = itemView.findViewById(R.id.callEmployee);

            context = itemView.getContext();


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, EmployeeProfileActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    int position = EmployeeAdapter.this.employeeList.get(getAdapterPosition()).getEmployeeId();
                    Log.e("Weekly wages", "Employee id is" + position);
                    i.putExtra("id", position);
                    context.startActivity(i);
                    return;
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + EmployeeAdapter.this.employeeList.get(getAdapterPosition()).getEmployeeMobile()));
                    MainActivity.employeeMobileNumber = EmployeeAdapter.this.employeeList.get(getAdapterPosition()).getEmployeeMobile();
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermission(callPermArray, REQUEST_CODE_CALL_PERMISSION);
                        return;
                    }
                    context.startActivity(callIntent);

                }
            });


        }

        private void requestPermission(String[] permissionArray, int requestCode) {
            ActivityCompat.requestPermissions(activity, permissionArray, requestCode);
        }

    }
}
