package com.example.trackit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder>{

    private List<ToDoModel> toDoList;
    private CheckListPage checkListPage;
    private UserDataBase userDataBase;

    public ToDoAdapter( UserDataBase userDataBase, CheckListPage checkListPage){
        this.userDataBase = userDataBase;
        this.checkListPage = checkListPage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_tasks_layout,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        userDataBase = new UserDataBase(getContext());
        userDataBase.openDatabase();
        final ToDoModel item = toDoList.get(position);

        holder.tasks.setText(item.getTasksList());
        holder.tasks.setChecked(toBoolean(item.getStatus()));
        holder.tasks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    userDataBase.updateStatus(item.getId(), 1);
                }else{
                    userDataBase.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return checkListPage;
    }

    public void setTasks(List<ToDoModel> toDoList){
        this.toDoList = toDoList;
        notifyDataSetChanged();
    }
    public void deleteTask(int position){
        ToDoModel item = toDoList.get(position);
        userDataBase.deleteToDo(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ToDoModel item = toDoList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTasksList());
        AddNewTasks fragment = new AddNewTasks();
        fragment.setArguments(bundle);
        fragment.show(checkListPage.getSupportFragmentManager(), AddNewTasks.TAG);
    }

    public int getItemCount() {
        return toDoList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox tasks;
        ViewHolder(View view){
            super(view);
            tasks = view.findViewById(R.id.tasksCheckBox);
        }
    }

}
