package oms.pc_protector.restApi.client.mapper;

import oms.pc_protector.restApi.client.model.ClientVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientMapper {

    public List<ClientVO> getClientList(String id);

    public int insertClientInfo(ClientVO clientVO);

    public List<ClientVO> selectClientAll();

    public List<String> selectClientById(String id);

    public boolean updateClientInfo(ClientVO clientVO);

    public void updateWrongMd5(ClientVO clientVO);
}
