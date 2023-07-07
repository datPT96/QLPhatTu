package pt.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.model.entity.PhatTu;
import pt.model.repository.PhatTuRepo;

@Service
public class PhTuDetailService implements UserDetailsService {
    private final PhatTuRepo phatTuRepo;

    public PhTuDetailService(PhatTuRepo phatTuRepo) {
        this.phatTuRepo = phatTuRepo;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PhatTu pt = phatTuRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Khong tim thay phat tu"));
        return PhatTuDetails.mapToPhatTu(pt);
    }
}
