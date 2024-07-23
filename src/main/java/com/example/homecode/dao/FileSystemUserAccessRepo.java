package com.example.homecode.dao;

import com.example.homecode.dto.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FileSystemUserAccessRepo implements UserAccessRepo {

    @Value("${filedb.datasource}")
    private String dataSource;

    @Override
    public <S extends UserAccess> S save(S entity) {
        synchronized (FileSystemUserAccessRepo.class) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataSource))) {
                String userId = entity.getUserId();
                List<String> endpoints = entity.getEndpoint();
                for (String endpoint : endpoints) {
                    bw.write(userId.concat(":").concat(endpoint).concat("\n"));
                }
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return entity;
        }
    }

    @Override
    public Optional<UserAccess> findById(String userId) {
        ArrayList<String> endpoints = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dataSource))){
            String line = br.readLine();
            while (line != null) {
                String[] userAndEp = line.split(":");
                if (userAndEp[0].equals(userId)) {
                    endpoints.add(userAndEp[1]);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        UserAccess userAccess = new UserAccess();
        userAccess.setUserId(userId);
        userAccess.setEndpoint(endpoints);
        return Optional.of(userAccess);
    }

    @Override
    public <S extends UserAccess> Iterable<S> saveAll(Iterable<S> entities) {
        // todo
        return null;
    }

    @Override
    public boolean existsById(String s) {
        // todo
        return false;
    }

    @Override
    public Iterable<UserAccess> findAll() {
        return null;
    }

    @Override
    public Iterable<UserAccess> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(UserAccess entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserAccess> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
