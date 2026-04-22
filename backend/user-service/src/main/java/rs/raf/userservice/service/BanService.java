package rs.raf.userservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import rs.raf.userservice.error.BanNotFoundException;
import rs.raf.userservice.model.Ban;
import rs.raf.userservice.repository.BanRepository;

@Service
public class BanService {
    private final BanRepository repo;

    public BanService(BanRepository repo) {
        this.repo = repo;
    }

    public List<String> getAll() {
        return repo.findAll().stream().map(Ban::getIpAddress).toList();
    }

    public void ban(String ip) {
        Ban ban = new Ban().setIpAddress(ip);
        repo.save(ban);
    }

    public void unban(String ip) {
        Ban ban = repo.findByIpAddress(ip)
            .orElseThrow(() -> new BanNotFoundException("Ban with IP address " + ip + " not found"));
        repo.delete(ban);
    }
}
