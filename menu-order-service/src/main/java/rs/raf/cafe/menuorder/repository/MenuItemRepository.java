package rs.raf.cafe.menuorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.cafe.menuorder.model.MenuItem;
import rs.raf.cafe.menuorder.model.MenuItemType;
import rs.raf.cafe.menuorder.model.Season;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByType(MenuItemType type);

    List<MenuItem> findBySeason(Season season);

    List<MenuItem> findByAvailableTrue();
}
