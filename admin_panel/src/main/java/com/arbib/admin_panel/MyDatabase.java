package com.arbib.admin_panel;

import com.arbib.admin_panel.interfaces.admin.AdminsReceiverListener;
import com.arbib.admin_panel.interfaces.operations.OperationsReceiverListener;
import com.arbib.admin_panel.interfaces.role.FindRoleListener;
import com.arbib.admin_panel.interfaces.role.RoleDateListener;
import com.arbib.admin_panel.interfaces.role.RolesReceiverListener;
import com.arbib.admin_panel.interfaces.skills.ListSkillsListener;
import com.arbib.admin_panel.interfaces.skills.skillListener;
import com.arbib.admin_panel.interfaces.skills.skillBoolean;
import com.arbib.admin_panel.interfaces.position.positionReceiverListener;
import com.arbib.admin_panel.interfaces.position.ListPositionListener;
import com.arbib.admin_panel.interfaces.position.PositionBoolean;
import com.arbib.admin_panel.interfaces.ResponseListener;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.arbib.admin_panel.objects.Admin;
import com.arbib.admin_panel.objects.Experience;
import com.arbib.admin_panel.objects.Operation;
import com.arbib.admin_panel.objects.Position;
import com.arbib.admin_panel.objects.Role;
import com.arbib.admin_panel.objects.Skill;
import com.arbib.admin_panel.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public final class MyDatabase {
    private Context context;
    private DatabaseReference skillRef;
    private DatabaseReference positionRef;
    private DatabaseReference experienceRef;
    private DatabaseReference userRef;
    private DatabaseReference adminRef;
    private DatabaseReference roleRef;
    private DatabaseReference operationRef;
    private FirebaseAuth auth;

    public MyDatabase(Context context) {
        this.skillRef = FirebaseDatabase.getInstance().getReference("skills");
        this.positionRef = FirebaseDatabase.getInstance().getReference("positions");
        this.experienceRef = FirebaseDatabase.getInstance().getReference("experiences");
        this.userRef = FirebaseDatabase.getInstance().getReference("users");
        this.adminRef = FirebaseDatabase.getInstance().getReference("admins");
        this.operationRef = FirebaseDatabase.getInstance().getReference("operations");
        this.roleRef = FirebaseDatabase.getInstance().getReference("roles");
        auth = FirebaseAuth.getInstance();
        this.context = context;
    }

    /**
     *
     * @return true if user already logged in
     */
    public Boolean checkUserLogin(){
        return auth.getCurrentUser() != null;
    }

    public void registerUser(String email, String password, ResponseListener listener){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())listener.onSuccess(true);
                        else listener.onError(new Exception());
                    }
                });
    }

    public void saveNewUserData(User user){
        HashMap<String,String > map = new HashMap<>();
        map.put("username",user.getUsername());
        map.put("firstname",user.getFirstname());
        map.put("lastname",user.getLastname());
        map.put("phone",user.getPhone());
        map.put("birthday",user.getBirthday());
        map.put("address",user.getAddress());
        String key = user.getId();
        userRef.child(key).setValue(map);
        DatabaseReference skills = userRef.child(key).child("skills");
        DatabaseReference experiences = userRef.child(key).child("experiences");

        HashMap<String,Object> hashMap = new HashMap<>();

        for(Skill skill: user.getSkills()){
            hashMap.put("libelle",skill.getLibelle());
            hashMap.put("description",skill.getDescription());
            skills.child(skill.getId()).setValue(hashMap);
            hashMap.clear();
        }
        for(Experience experience: user.getExperiences()){
            hashMap.put("companyName", experience.getCompanyName());
            hashMap.put("jobTitle", experience.getJobTitle());
            hashMap.put("from", experience.getFrom());
            hashMap.put("to", experience.getFrom());
            hashMap.put("description", experience.getDescription());
            experiences.push().setValue(hashMap);
            hashMap.clear();
        }

    }

    public void singOut(){auth.signOut();}

    public FirebaseUser getCurrentUser(){return auth.getCurrentUser();}

    public String getCurrentUserID(){return auth.getCurrentUser().getUid();}

    public String getCurrentUserEmail(){
        return auth.getCurrentUser().getEmail();
    }

    public void loginUser(String email, String password, ResponseListener listener){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())listener.onSuccess(true);
                        else listener.onError(new Exception());
                    }
                });
    }


    //skill's methods

    public void readSkillByID(String id, final skillListener listener){
        skillRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean id_exist = false;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals(id)){
                        Skill skill = new Skill(dataSnapshot.child("libelle").getValue().toString(),
                                dataSnapshot.child("description").getValue().toString());
                        listener.onSkillReceived(skill);
                        id_exist = true;
                    }
                }
                if(!id_exist){
                    Toast.makeText(context, "id not found", Toast.LENGTH_SHORT).show();
                    listener.onError(new Exception());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "id not found", Toast.LENGTH_SHORT).show();
                listener.onError(new Exception());
            }
        });
    }

    public void getAllSkills(final ListSkillsListener listener){
        skillRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Skill> skills = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                     skills.add(new Skill(dataSnapshot.getKey(),dataSnapshot.child("libelle").getValue().toString(),
                             dataSnapshot.child("description").getValue().toString()));
                }
                listener.onSkillsReceived(skills);
                if(skills.isEmpty()){
                    listener.onError(new Exception());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(new Exception());
            }
        });
    }

    public void addSkill(Skill skill){
        HashMap<String,String > map = new HashMap<>();
        map.put("libelle", skill.getLibelle());
        map.put("description", skill.getDescription());
        this.skillRef.push().setValue(map);
    }

    public void modifySkillByID(String id, Skill skill, final skillBoolean listener){
        skillRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Boolean id_exist = false;
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.getKey().equals(id)){
                            skillRef.child(dataSnapshot.getKey()).child("libelle").setValue(skill.getLibelle());
                            skillRef.child(dataSnapshot.getKey()).child("description").setValue(skill.getDescription());
                            listener.onSuccess(true);
                            id_exist = true;
                        }
                    }
                    if(!id_exist){
                        listener.onError(new Exception());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(new Exception());
            }
        });
    }

    public void deleteSkillByID(String id,final skillBoolean listener){
        skillRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean id_exist = false;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals(id)){
                        skillRef.child(dataSnapshot.getKey()).removeValue();
                        listener.onSuccess(true);
                        id_exist = true;
                    }
                }
                if(!id_exist){
                    listener.onError(new Exception());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(new Exception());
            }
        });
    }

    public void deleteSkillByFilter(String filter){
        skillRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("libelle").getValue().toString().contains(filter)){
                        skillRef.child(dataSnapshot.getKey()).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    // position's methods

    public void addPosition(Position position){
        HashMap<String,String > map = new HashMap<>();
        map.put("libelle", position.getLibelle());
        map.put("description", position.getDescription());
        this.positionRef.push().setValue(map);
    }

    public void readPositionByID(String id,final positionReceiverListener listener){
        positionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean id_exist = false;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals(id)){
                        Position position = new Position(dataSnapshot.child("libelle").getValue().toString(),
                                dataSnapshot.child("description").getValue().toString());
                        listener.onReceive(position);
                        id_exist = true;
                    }
                }
                if(!id_exist){
                    listener.onError(new Exception());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(new Exception());
            }
        });
    }

    public void getAllPositions(final ListPositionListener listener){
        positionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Position> positions = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    positions.add(new Position(dataSnapshot.getKey(),dataSnapshot.child("libelle").getValue().toString(),
                            dataSnapshot.child("description").getValue().toString()));
                }
                listener.onReceived(positions);
                if(positions.isEmpty()){
                    listener.onError(new Exception());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(new Exception());
            }
        });
    }

    public void modifyPositionByID(String id, Position position, final PositionBoolean listener){
        positionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Boolean id_exist = false;
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.getKey().equals(id)){
                            positionRef.child(dataSnapshot.getKey()).child("libelle").setValue(position.getLibelle());
                            positionRef.child(dataSnapshot.getKey()).child("description").setValue(position.getDescription());
                            listener.onSuccess(true);
                            id_exist = true;
                        }
                    }
                    if(!id_exist){
                        listener.onError(new Exception());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(new Exception());
            }
        });
    }

    public void deletePositionByID(String id, final PositionBoolean listener){
        positionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean id_exist = false;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals(id)){
                        positionRef.child(dataSnapshot.getKey()).removeValue();
                        listener.onSuccess(true);
                        id_exist = true;
                    }
                }
                if(!id_exist){
                    listener.onError(new Exception());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(new Exception());
            }
        });
    }

    public void deletePositionByFilter(String filter){
        positionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("libelle").getValue().toString().contains(filter)){
                        positionRef.child(dataSnapshot.getKey()).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addAdmin(Admin admin){
        HashMap<String,String> map = new HashMap<>();

        map.put("first name", admin.getFirstname());
        map.put("last name", admin.getLastname());
        map.put("birthday", admin.getBirthday());
        map.put("phone", admin.getPhone());
        map.put("email", admin.getEmail());
        map.put("role", admin.getRole());
        map.put("state", admin.getState());
        adminRef.child(admin.getId()).setValue(map);
    }

    public void getAllAdmins(final AdminsReceiverListener listener){

        adminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Admin> admins = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    admins.add(new Admin(
                            dataSnapshot.getKey(),
                            dataSnapshot.child("first name").getValue().toString(),
                            dataSnapshot.child("last name").getValue().toString(),
                            dataSnapshot.child("email").getValue().toString(),
                            dataSnapshot.child("birthday").getValue().toString(),
                            dataSnapshot.child("role").getValue().toString(),
                            dataSnapshot.child("phone").getValue().toString(),
                            dataSnapshot.child("state").getValue().toString()));
                }
                listener.OnReceive(admins);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.OnError(new Exception());
            }
        });
    }

    public void adminUpdate(Admin admin){
        DatabaseReference admin_id = adminRef.child(admin.getId());
        admin_id.child("first name").setValue(admin.getFirstname());
        admin_id.child("last name").setValue(admin.getLastname());
        admin_id.child("email").setValue(admin.getEmail());
        admin_id.child("birthday").setValue(admin.getBirthday());
        admin_id.child("role").setValue(admin.getRole());
        admin_id.child("phone").setValue(admin.getPhone());
        admin_id.child("state").setValue(admin.getState());

    }

    public void getLastOperationCode(final ResponseListener listener){
        Query query = operationRef.orderByKey().limitToLast(1);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap :  snapshot.getChildren()){
                    listener.onSuccess(snap.getKey());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getOperationByCode(String code, ResponseListener listener){
        operationRef.child(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(new Operation(code,
                                snapshot.child("libelle").getValue().toString(),
                                snapshot.child("description").getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error.toException());
            }
        });
    }

    public void insertRole(Role role){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        HashMap<String, String> roleMap = new HashMap<>();
        roleMap.put("libelle", role.getLibelle());
        roleMap.put("description", role.getDescription());
        roleMap.put("create at", dateFormat.format(new Date()));
        roleMap.put("update at", "-");
        roleMap.put("nbr operations", String.valueOf(role.getNbr_operations()));

        String id = roleRef.push().getKey();

        roleRef.child(id).setValue(roleMap);

        HashMap<String, String> operMap = new HashMap<>();

        for(Operation op : role.getOperations()){
            operMap.put(op.getCode(), op.getLibelle());
        }

        roleRef.child(id).child("operations").setValue(operMap);
    }


    public void updateRole(Role role){
        DatabaseReference target_role = roleRef.child(role.getId());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        target_role.child("libelle").setValue(role.getLibelle());
        target_role.child("description").setValue(role.getDescription());
        target_role.child("nbr operations").setValue(role.getNbr_operations());
        target_role.child("update at").setValue(dateFormat.format(new Date()));
        target_role.child("libelle").setValue(role.getLibelle());

        HashMap<String, String> map = new HashMap<>();

        for(Operation op : role.getOperations()){
            map.put(op.getCode(), op.getLibelle());
        }

        target_role.child("operations").setValue(map);
    }


    public void roleExist(ArrayList<String> operations, String libelle, FindRoleListener listener){
        roleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean exist = false;
                for(DataSnapshot baseSnap : snapshot.getChildren()){
                    if(baseSnap.child("libelle").getValue().toString().equals(libelle)){
                        exist = true;
                        break;
                    }
                    DataSnapshot insideSnap = baseSnap.child("operations");
                    ArrayList<String> tmpOperations = new ArrayList<>();
                    for(DataSnapshot snap : insideSnap.getChildren()){
                        tmpOperations.add(snap.getValue().toString());
                    }
                    Collections.sort(operations);
                    Collections.sort(tmpOperations);

                    if(operations.equals(tmpOperations)) exist = true;
                }

                listener.OnFound(exist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void getRoleByLibelle(String libelle, ResponseListener listener){
        roleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("libelle").getValue().toString().equals(libelle)){
                        DataSnapshot operSnap = dataSnapshot.child("operations");
                        ArrayList<Operation> operations = new ArrayList<>();
                        for(DataSnapshot snap : operSnap.getChildren()){
                            operations.add(new Operation(snap.getKey(), snap.getValue().toString()));
                        }
                        listener.onSuccess(new Role(
                                dataSnapshot.getKey(),
                                dataSnapshot.child("libelle").getValue().toString(),
                                dataSnapshot.child("description").getValue().toString(),
                                Integer.parseInt(dataSnapshot.child("nbr operations").getValue().toString()),
                                operations ));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error.toException());
            }
        });
    }


    public void deleteRole(String libelle, ResponseListener listener){
        roleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    if(snap.child("libelle").getValue().toString().equals(libelle)){
                        roleRef.child(snap.getKey()).removeValue();
                        break;
                    }
                }
                adminRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(snap.child("role").getValue().toString().equals(libelle)){
                                adminRef.child(Objects.requireNonNull(snap.getKey())).child("role").setValue("no role");
                            }
                        }
                        listener.onSuccess(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onError(error.toException());
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAllRoles(final RolesReceiverListener listener){
        roleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Role> roles = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ArrayList<Operation> operations = new ArrayList<>();
                    DataSnapshot operSnap = dataSnapshot.child("operations");
                    for(DataSnapshot snap : operSnap.getChildren()){
                        operations.add(new Operation(snap.getKey(), snap.getValue().toString()));
                    }
                    roles.add(new Role(dataSnapshot.getKey(),
                            dataSnapshot.child("libelle").getValue().toString(),
                            dataSnapshot.child("description").getValue().toString(),
                            Integer.parseInt(dataSnapshot.child("nbr operations").getValue().toString()),
                            operations));

                }

                listener.OnReceive(roles);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.OnError(new Exception());
            }
        });
    }

    public void checkRoleName(String id ,String name, FindRoleListener listener){
        roleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean exist = false;
                for(DataSnapshot snap : snapshot.getChildren()){
                    if( !snap.getKey().equals(id) && snap.child("libelle").getValue().toString().equals(name)){
                        exist = true;
                        break;
                    }
                }
                listener.OnFound(exist);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getRoleDates(String id, RoleDateListener listener){
        roleRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HashMap<String, String> roleDate = new HashMap<>();

                if(snapshot.child("create at").getValue() != null &&
                        snapshot.child("update at").getValue() != null){
                    roleDate.put("create at", snapshot.child("create at").getValue().toString());
                    roleDate.put("update at", snapshot.child("update at").getValue().toString());

                    listener.OnReceive(roleDate);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.OnError(error.toException());
            }
        });
    }


    public void getAllOperations(final OperationsReceiverListener listener){
        operationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Operation> operations = new ArrayList<>();
                for(DataSnapshot snap : snapshot.getChildren()){
                    operations.add(new Operation(snap.getKey(),
                            snap.child("libelle").getValue().toString(),
                            snap.child("description").getValue().toString()));
                }
                listener.onReceive(operations);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void insertOperation(Operation op){
        HashMap<String , String> map = new HashMap<>();
        map.put("libelle", op.getLibelle());
        map.put("description", op.getDescription());
        operationRef.child(op.getCode()).setValue(map);
    }

    private void checkRoleIDExist(String id, ResponseListener listener){
        roleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exist = false;
                for(DataSnapshot snap : snapshot.getChildren()){
                    if(snap.getKey().equals(id)){
                        exist = true;
                        break;
                    }
                }
                listener.onSuccess(exist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error.toException());
            }
        });
    }

    public void getAssociatedOperations(String id, final OperationsReceiverListener listener){
        checkRoleIDExist(id, new ResponseListener() {
            @Override
            public void onSuccess(Object object) {
                boolean exist = (boolean) object;
                if(exist){
                    roleRef.child(id).child("operations").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<Operation> operations = new ArrayList<>();
                            for(DataSnapshot snap : snapshot.getChildren()){
                                operations.add(new Operation(snap.getKey(), snap.getValue().toString()));
                            }
                            listener.onReceive(operations);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }else {
                    Toast.makeText(context, "role doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }


    public void addOperationToRole(String id, Operation operation){
        checkRoleIDExist(id, new ResponseListener() {
            @Override
            public void onSuccess(Object object) {
                if((boolean) object){
                    roleRef.child(id).child("operations").child(operation.getCode()).setValue(operation.getLibelle());
                }else {
                    Toast.makeText(context, "role was deleted by another admin", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }






}
