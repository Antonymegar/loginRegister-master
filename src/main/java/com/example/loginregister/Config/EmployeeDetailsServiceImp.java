package com.example.loginregister.Config;

import com.example.loginregister.Repositories.EmployeeRepository;
import com.example.loginregister.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EmployeeDetailsServiceImp implements UserDetailsService {
   @Autowired
   private EmployeeRepository Erepo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Employee employee = Erepo.getEmployeeByUsername(username);
        if (employee == null) {
            throw new UsernameNotFoundException("Could not find employee");
        }

        return new EmployeeDetails(employee);
    }
}
