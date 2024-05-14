package enset.android.projetsynthese.ui.UserChat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import enset.android.projetsynthese.R;
import enset.android.projetsynthese.databinding.FragmentUserChatBinding;
import enset.android.projetsynthese.ui.UserChat.SignInActivity;
import enset.android.projetsynthese.ui.UserChat.adapters.UserAdapter;
import enset.android.projetsynthese.ui.UserChat.models.User;

public class UserChatFragment extends Fragment {

    private FragmentUserChatBinding binding;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    String yourName;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = root.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        String userName = getArguments() != null ? getArguments().getString("username") : "User";
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(userName);

        userAdapter = new UserAdapter(requireContext());
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userAdapter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String uid=dataSnapshot.getKey();
                    User user=dataSnapshot.getValue(User.class);
                    if (user!=null && user.getUserId()!=null && !user.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        userAdapter.addUser(user);
                    }
                }
                List<User> usersList=userAdapter.getUsers();
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Enable options menu
        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            // Logout action
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(requireActivity(), SignInActivity.class));
            requireActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
